package com.example.childmonitoring;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText email , pass;
    private FirebaseAuth mAuth;
    AutoCompleteTextView  spinner;
    List<String> list ;
    String seletedItem="";
    ArrayAdapter<String> arrayAdapter;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.LoginAs);
        list = new ArrayList<>();
        list.add("Admin");
        list.add("Parent");
        arrayAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,list);
        spinner.setText(arrayAdapter.getItem(0).toString(), false);
        spinner.setAdapter(arrayAdapter);
        Button btn =(Button)findViewById(R.id.btnLogin);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btn.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
        }
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               seletedItem = list.get(position);
           }
       });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (seletedItem.equals(list.get(0))) {

                    email = (EditText) findViewById(R.id.txtEmail);
                    pass = (EditText) findViewById(R.id.txtPwd);
                    if (email.getText().toString() == null || pass.getText().toString() == null || email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                        email.setError("Invalid Email");
                        pass.setError("Invalid  Pass");
                    }else{
                        if(email.getText().toString().equals("Admin") && pass.getText().toString().equals("Admin")){
                            Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_LONG).show();
                        }
                    }


                } else {
                    email = (EditText) findViewById(R.id.txtEmail);
                    pass = (EditText) findViewById(R.id.txtPwd);
                    if (email.getText().toString() == null || pass.getText().toString() == null || email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
                        email.setError("Invalid Email");
                        pass.setError("Invalid  Pass");
                    } else {

                        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information

                                            Log.d("", "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Intent intent = new Intent(MainActivity.this, Parents.class);
                                            startActivity(intent);

                                        } else {

                                            // If sign in fails, display a message to the user.
                                            Log.w("", "signInWithEmail:failure", task.getException());

                                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                        // ...
                                    }
                                });
                    }


                    // if(email.getText().toString().equals("Admin") && pass.getText().toString().equals("Pass")) {

                    //}
                    //else{
                    //   Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        });


    }
}