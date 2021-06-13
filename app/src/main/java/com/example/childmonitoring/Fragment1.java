package com.example.childmonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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




        userLogin();

        return view;

    }
    private void userLogin() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.thinkSpeek,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      try {
                            JSONObject obj = new JSONObject(response);

                           // JSONObject userJson = obj.getJSONObject("channel");
                          JSONArray jsonArray = obj.getJSONArray("feeds");
                          for (int i=0;i<jsonArray.length();i++){
                              int a =jsonArray.getJSONObject(i).getInt("field1");
                              if(1==a) {
                                  int b = jsonArray.getJSONObject(i).getInt("field2");
                                  if (b == 0) {
                                      Toast.makeText(getContext(), "At Home", Toast.LENGTH_LONG).show();
                                  } else if (b == 1) {
                                      Toast.makeText(getContext(), "In Bus, on way to school", Toast.LENGTH_LONG).show();
                                  }else if (b == 2) {
                                      Toast.makeText(getContext(), "In School", Toast.LENGTH_LONG).show();
                                  }else if (b == 3) {
                                      Toast.makeText(getContext(), "In Bus way to home", Toast.LENGTH_LONG).show();
                                  }
                              }
                          }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
