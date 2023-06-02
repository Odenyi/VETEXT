package com.example.vetext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder>  {
    ArrayList<Vetlistclass>datalist;
    public myadapter(ArrayList<Vetlistclass>datalist){
        this.datalist=datalist;
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_after_registration,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
      holder.t4.setText(datalist.get(position).getAdmin());
        holder.t2.setText(datalist.get(position).getEmail());
        holder.t1.setText(datalist.get(position).getUname());
        holder.t3.setText(datalist.get(position).getNumber());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3,t4;

        public myviewholder(@NonNull View itemView) {

            super(itemView);
            t1=itemView.findViewById(R.id.t);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);

        }
    }
}
