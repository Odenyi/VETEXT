package com.example.vetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    //start authentication
    private FirebaseAuth mauth;
    private Button register, login;
    private EditText editemail, editnumber, editusername, editpassword;
    ProgressBar regprogressBar;
    FirebaseFirestore fstore;
    String UserID;
    CheckBox Vet,Farmer,Officer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mauth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login1);
        register = findViewById(R.id.register1);
        editemail = findViewById(R.id.email1);
        editnumber = findViewById(R.id.number);
        editusername=findViewById(R.id.username);
        Vet=findViewById(R.id.checkBox);
        Farmer=findViewById(R.id.checkBox2);
        Officer=findViewById(R.id.checkBox3);


        editpassword = findViewById(R.id.password1);
        regprogressBar=findViewById(R.id.progressBar);
        fstore=FirebaseFirestore.getInstance();
        Vet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Vet.isChecked()){
                    Farmer.setChecked(false);
                    Officer.setChecked(false);
                }
            }
        });
        Farmer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Farmer.isChecked()){
                    Vet.setChecked(false);
                    Officer.setChecked(false);
                }
            }
        });
        Officer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(Officer.isChecked()){
                    Farmer.setChecked(false);
                    Vet.setChecked(false);
                }
            }
        });
       /*if (mauth.getCurrentUser()!=null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=editemail.getText().toString();
                String phonenumber=editnumber.getText().toString();
                String password= editpassword.getText().toString();
                String username= editusername.getText().toString();
                if(TextUtils.isEmpty(username)){
                    editusername.setError("Username required!");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    editemail.setError("Email required!");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editemail.setError("Email invalid!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editpassword.setError(("password required!"));
                    return;
                }
                if(password.length()<6){
                    editpassword.setError("The password less than 6 characters");
                    return;
                }
                if(TextUtils.isEmpty(phonenumber)){
                    editnumber.setError("number is required!");
                    return;
                }
                // on check of check boxes
                if(!(Vet.isChecked()||Farmer.isChecked()||Officer.isChecked())){
                    Toast.makeText(Register.this, "Select the account", Toast.LENGTH_SHORT).show();
                    return;
                }
               // regprogressBar.setVisibility(View.VISIBLE);
                //register user on firebase
                mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                           //to store data in firestore here is  the code
                            UserID=mauth.getCurrentUser().getUid();
                            DocumentReference documentref=fstore.collection("users").document(UserID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("uname", username);
                            user.put("email", email);
                            user.put("number",phonenumber);
                            //specify the  user is admin
                            if(Farmer.isChecked()){
                                user.put("normaluser","farmer");

                            }
                            if(Vet.isChecked()){
                                user.put("admin","veterinary doctor");

                            }

                            if(Officer.isChecked()){
                                user.put("extension","Extension Officer");

                            }
                            documentref.set(user);

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            return;



                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Error"+e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


    }
