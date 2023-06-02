package com.example.vetext;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AfterregistrationDoctorFrag extends Fragment {
    RecyclerView doctorlist;
    FirebaseFirestore fstore;
    ArrayList<Vetlistclass> datalist;
    myadapter adpter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_afterregistration_doctor, container, false);
        doctorlist = view.findViewById(R.id.vetlist);
        doctorlist.setLayoutManager(new LinearLayoutManager(getContext()));
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
        return view;
    }
}