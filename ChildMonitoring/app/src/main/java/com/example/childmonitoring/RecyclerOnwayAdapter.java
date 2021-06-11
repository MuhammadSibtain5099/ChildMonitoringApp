package com.example.childmonitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerOnwayAdapter extends RecyclerView.Adapter <RecyclerOnwayAdapter.OnWayViewHolder> {
    ArrayList<String> al;
    public RecyclerOnwayAdapter(ArrayList<String> arrayList){
        al = arrayList;
    }
    @NonNull
    @Override
    public OnWayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnWayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.onway,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OnWayViewHolder holder, int position) {

        holder.textVie.setText(al.get(position));

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    class OnWayViewHolder extends RecyclerView.ViewHolder{
        public TextView textVie;

        public OnWayViewHolder(@NonNull View itemView) {
            super(itemView);
            textVie=itemView.findViewById(R.id.Name);
        }
    }
}
