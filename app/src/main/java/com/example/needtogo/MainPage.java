package com.example.needtogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.makeText;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void map(View view) {
        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }

    public void add(View view) {
        startActivity(new Intent(getApplicationContext(), WashroomData.class));
    }

    public void info(View view) {
        startActivity(new Intent(getApplicationContext(), Information.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}