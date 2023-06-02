package com.example.vetext;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity{
    private  Button login,register,forgotpassword;
    private EditText logemail,logpassword;
    FirebaseAuth mauth;
    ProgressBar logprogressbar;
    FirebaseFirestore fstore;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotpassword = findViewById(R.id.forgotpassword);
        logemail=findViewById(R.id.email);
        logpassword=findViewById(R.id.password);
        logprogressbar=findViewById(R.id.progressBar);
        mauth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=logemail.getText().toString().trim();
                String password=logpassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    logemail.setError("Email required!");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    logemail.setError("Email invalid!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    logpassword.setError(("password required!"));
                    return;
                }
                if(password.length()<6){
                    logpassword.setError("The password less than 6 characters");
                }
                logprogressbar.setVisibility(View.VISIBLE);
                // check for aunthentication verification in firebase
                mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "login succefull", Toast.LENGTH_SHORT).show();
                            //check if user is admin or normal and extract data from firestore
                            UserID=mauth.getCurrentUser().getUid();
                            DocumentReference documentref=fstore.collection("users").document(UserID);
                            documentref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Log.d("TAG","onsuccess"+documentSnapshot.getData());
                                    // now identify prevelgde
                                    if(documentSnapshot.getString("admin")!=null){
                                        //go to vet activity
                                        startActivity(new Intent(getApplicationContext(),VetDoctor.class));
                                        return;
                                    }
                                    if(documentSnapshot.getString("normaluser")!=null) {
                                        //go to vet activity
                                        startActivity(new Intent(getApplicationContext(), AfterRegistration.class));
                                        return;
                                    }
                                    if(documentSnapshot.getString("extension")!=null) {
                                            //go to vet activity
                                            startActivity(new Intent(getApplicationContext(), Extension.class));
                                            return;
                                    }

                                    if(documentSnapshot.getString("normaluser")==null && documentSnapshot.getString("admin")==null && documentSnapshot.getString("extension")==null); {
                                        //go to vet activity
                                        startActivity(new Intent(getApplicationContext(), AfterRegistration.class));
                                        return;
                                    }

                                }
                            });

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            logprogressbar.setVisibility(View.GONE);
                        }




                    }
                });


            }
        });
        //sent forget password link
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetmail=new  EditText(v.getContext());
                final AlertDialog.Builder passResetDialog=new AlertDialog.Builder(v.getContext());
                passResetDialog.setTitle("Reset Password?");
                passResetDialog.setMessage("Enter email to receive link");
                passResetDialog.setView(resetmail);

                passResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //extract email and sent link
                        String mail= resetmail.getText().toString().trim();
                        mauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Email link successfully sent", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error!Email link not sent"+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passResetDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if  you cancel close
                    }
                });
                //to display the dialog
                passResetDialog.create().show();
            }
        });

    }
}