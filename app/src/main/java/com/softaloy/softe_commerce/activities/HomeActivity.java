package com.softaloy.softe_commerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.softaloy.softe_commerce.R;

public class HomeActivity extends AppCompatActivity {

    ProgressBar progressbar;
    FirebaseAuth auth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);
        if (auth.getCurrentUser()!=null){
            progressbar.setVisibility(View.VISIBLE);
            startActivity(new Intent(HomeActivity.this, Dashboard.class));
            Toast.makeText(this, "Please wait... You are already logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void singin(View view) {

        startActivity(new Intent(HomeActivity.this, Login.class));
    }

    public void singup(View view) {

        startActivity(new Intent(HomeActivity.this, Registration.class));

    }
}