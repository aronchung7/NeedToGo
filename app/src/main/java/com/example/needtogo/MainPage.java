package com.example.needtogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
        //If you want to pass the unique user to different activities
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String val = extras.getString("email");
//            Intent intent = new Intent(getApplicationContext(), Add.class);
//            intent.putExtra("email", val);
//            startActivity(intent);
//        }
        startActivity(new Intent(getApplicationContext(), Add.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}