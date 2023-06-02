package com.example.vetext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.concurrent.Executor;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerDetailsfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerDetailsfrag extends Fragment {
    public CustomerDetailsfrag(){}

    FirebaseAuth fauth;
    FirebaseFirestore firestore;
    String userid;
    FirebaseUser User;
    EditText email,username,number;
    Button button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View veiw= inflater.inflate(R.layout.fragment_customer_detailsfrag, container, false);
        fauth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        email=veiw.findViewById(R.id.mail);
        username=veiw.findViewById(R.id.username);
        number= veiw.findViewById(R.id.phonenumber);

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



        return veiw;
    }

}