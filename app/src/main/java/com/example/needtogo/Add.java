package com.example.needtogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.makeText;

public class Add extends AppCompatActivity {

    EditText name, address, description;
    RatingBar rating;
    Switch availability;
    String val = "anonymous";
    Button btn;

    public static final String NAME_KEY = "name";
    public static final String ADDRESS_KEY = "address";
    public static final String DESCRIPTION_KEY = "description";
    public static final String RATING_KEY = "rating";
    public static final String AVAILABILITY_KEY = "availability";
    private DocumentReference mDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //If you want to pass the unique user to different activities
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            val = extras.getString("email");
//        }

        btn = findViewById(R.id.addSubmitButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = findViewById(R.id.addName);
                address = findViewById(R.id.addAddress);
                description = findViewById(R.id.addDescription);
                availability = findViewById(R.id.switch1);
                rating = findViewById(R.id.addRating);

                String aName = name.getText().toString();
                String aAddress = address.getText().toString();
                String aDescription = description.getText().toString();
                final boolean aAvailability = availability.isChecked();
                float aRating = rating.getRating();

                if (aName.isEmpty() || aAddress.isEmpty()) {
                    return;
                }

                mDocRef = FirebaseFirestore.getInstance().document("washrooms/"+aName);

                Map<String, Object> data = new HashMap<>();
                data.put(ADDRESS_KEY, aAddress);
                data.put(DESCRIPTION_KEY, aDescription);
                data.put(AVAILABILITY_KEY, aAvailability);
                data.put(RATING_KEY, aRating);

                mDocRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            makeText(Add.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainPage.class);
                            intent.putExtra("email", val);
                            startActivity(intent);
                        }
                        else {
                            makeText(Add.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}