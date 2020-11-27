package com.example.needtogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    CardView login, signUp;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.buttonLogin);
        signUp = findViewById(R.id.buttonSignUp);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainPage.class));
            finish();
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    username.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Password is required.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            makeText(MainActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                            //If you want to pass the unique user to different activities
//                            Intent intent = new Intent(getApplicationContext(), MainPage.class);
//                            intent.putExtra("email", email);
//                            startActivity(intent);
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                        }
                        else {
                            makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    username.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    password.setError("Password is required.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            makeText(MainActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                            //If you want to pass the unique user to different activities
//                            Intent intent = new Intent(getApplicationContext(), MainPage.class);
//                            intent.putExtra("email", email);
//                            startActivity(intent);
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                        }
                        else {
                            makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}