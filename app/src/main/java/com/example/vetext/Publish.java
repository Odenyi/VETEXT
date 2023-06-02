package com.example.vetext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class Publish extends Fragment {
    FirebaseFirestore fstore;
    EditText editText;
    Button button,loadbutton;
    View view;
    String UserID;
    FirebaseAuth mauth;
    DocumentReference docref;
    CollectionReference coref;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_publish, container, false);
        button =  view.findViewById(R.id.publish);
        loadbutton= view.findViewById(R.id.Load);
        fstore=FirebaseFirestore.getInstance();
        mauth=FirebaseAuth.getInstance();
        TextView textView=view.findViewById(R.id.loadpublish);
        //multiple add documents
        coref=fstore.collection("Tittle");
        // initialize document refrence
        docref=fstore.document("tittle/from extension officer");
        editText=view.findViewById(R.id.editpublish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=editText.getText().toString().trim();
              //get from class constructor and geters
                NotePublish note=new NotePublish(text);

                coref.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Published", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error"+e, Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        loadbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                coref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String input="";
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            NotePublish note=documentSnapshot.toObject(NotePublish.class);
                            String title=note.getTittle();
                            input += "Tittle" +title +"\n";
                            textView.setText("");


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "error"+e, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        return view;
    }

}


