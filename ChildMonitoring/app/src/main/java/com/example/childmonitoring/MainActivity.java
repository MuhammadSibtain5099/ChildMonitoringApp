package com.example.childmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email , pass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Button btn =(Button)findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText)findViewById(R.id.txtEmail);
                pass = (EditText)findViewById(R.id.txtPwd);
                if(email.getText().toString()==null|| pass.getText().toString()==null||email.getText().toString().isEmpty()|| pass.getText().toString().isEmpty()){
                    email.setError("Invalid Email");
                    pass.setError("Invalid  Pass");
                }
                else {

                    mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Log.d("", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
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
        });


    }
}