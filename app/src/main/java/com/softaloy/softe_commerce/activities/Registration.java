package com.softaloy.softe_commerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.models.UserModels;

public class Registration extends AppCompatActivity {

    EditText name, email, password;

    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        if (auth.getCurrentUser() != null){

            startActivity(new Intent(Registration.this, Dashboard.class));
            finish();
        }

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

    }

    public void singin(View view) {

        startActivity(new Intent(Registration.this, Login.class));
    }

    public void singup(View view) {

        progressBar.setVisibility(View.VISIBLE);

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length()<6){

            Toast.makeText(this, "Password too short, Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            UserModels userModels = new UserModels(name, email, password);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("users").child(id).setValue(userModels);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(Registration.this, "Succesfully Register", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Registration.this, Dashboard.class));
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Registration.this, "Registration Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}