package com.example.childmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Parents extends AppCompatActivity {


    TextView status,timeStutus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);
        int a =1;
        userLogin(1);
        status= findViewById(R.id.stStatus);
        timeStutus =findViewById(R.id.stTime);

    }



    private void userLogin(final int id) {


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
                                if(id==a) {
                                    int b = jsonArray.getJSONObject(i).getInt("field2");
                                    String time = jsonArray.getJSONObject(i).getString("created_at");
                                    if (b == 0) {
                                       // Toast.makeText(getContext(), "At Home", Toast.LENGTH_LONG).show();
                                         status.setText("At Home");
                                    } else if (b == 1) {
                                       // Toast.makeText(getContext(), "In Bus, on way to school", Toast.LENGTH_LONG).show();
                                        status.setText("In Bus,On the Way . Going to School");
                                    }else if (b == 2) {
                                        status.setText(" In School");
                                    }else if (b == 3) {
                                        Toast.makeText(getApplicationContext(), "In Bus way to home", Toast.LENGTH_LONG).show();
                                        status.setText("In Bus way to Home");

                                    }
                                    timeStutus.setText(time);
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}