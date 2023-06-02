package com.example.vetext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Afterregestrationdocactivty extends AppCompatActivity {
    RecyclerView doctorlist;
    FirebaseFirestore fstore;
    ArrayList<Vetlistclass> datalist;
    myadapter adpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterregestrationdocactivty);
        doctorlist = findViewById(R.id.vetlist);
        doctorlist.setLayoutManager(new LinearLayoutManager(this));
        fstore = FirebaseFirestore.getInstance();
        datalist=new ArrayList<>();
        adpter  = new myadapter(datalist);
        doctorlist.setAdapter(adpter);
        //query for firebase
        fstore.collection("users").whereEqualTo("admin","veterinary doctor").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot vetinary : list) {
                            Vetlistclass obj = vetinary.toObject(Vetlistclass.class);
                            datalist.add(obj);
                        }
                        adpter.notifyDataSetChanged();
                    }
                });

    }
}