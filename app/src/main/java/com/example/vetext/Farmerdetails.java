package com.example.vetext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Farmerdetails extends AppCompatActivity {
    FirebaseAuth fauth;
    FirebaseFirestore firestore;
    String userid;
    FirebaseUser User;
    TextView email,username,number;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmerdetails);
        fauth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        email=findViewById(R.id.textView6);
        username=findViewById(R.id.textView7);
        number= findViewById(R.id.textView8);

        userid=fauth.getCurrentUser().getUid();
        DocumentReference docRef = firestore.collection("users").document(userid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //com.google.firebase.firestore.auth.User user = documentSnapshot.toObject(User.class);
                username.setText(documentSnapshot.getString("uname"));
                number.setText(documentSnapshot.getString("number"));
                email.setText(documentSnapshot.getString("email"));
            }
            });

    }
}