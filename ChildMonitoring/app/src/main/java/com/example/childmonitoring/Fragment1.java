package com.example.childmonitoring;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment1 extends Fragment {

    HashMap<String,Object> data;
    RecyclerView recyclerView;
    RecyclerOnwayAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout,container,false);
        recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Children")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                data=(HashMap<String, Object>)document.getData();
                                arrayList.add(data.get("CName").toString());
                                Log.d("Data", document.getId() + " => " + document.getData());
                            }
                            adapter = new RecyclerOnwayAdapter(arrayList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.w("error", "Error getting documents.", task.getException());
                        }
                    }
                });






        return view;

    }

    public void UpdateUi(){

    }

}
