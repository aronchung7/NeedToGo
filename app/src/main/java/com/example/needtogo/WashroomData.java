package com.example.needtogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.makeText;

public class WashroomData extends AppCompatActivity {

    EditText name, address, city, country, description;
    RatingBar rating;
    Switch availability;
    CardView btn, dBtn;

//    public static final String NAME_KEY = "name";
    public static final String ADDRESS_KEY = "address";
    public static final String CITY_KEY = "city";
    public static final String COUNTRY_KEY = "country";
    public static final String DESCRIPTION_KEY = "description";
    public static final String RATING_KEY = "rating";
    public static final String AVAILABILITY_KEY = "availability";
    private DocumentReference mDocRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washroom_data);

        btn = findViewById(R.id.addSubmitButton);
        dBtn = findViewById(R.id.addDeleteButton);

        name = findViewById(R.id.addName);
        address = findViewById(R.id.addAddress);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = findViewById(R.id.addCity);
                country = findViewById(R.id.addCountry);
                description = findViewById(R.id.addDescription);
                availability = findViewById(R.id.switch1);
                rating = findViewById(R.id.addRating);

                String aName = name.getText().toString();
                String aAddress = address.getText().toString();
                String aCity = city.getText().toString();
                String aCountry = country.getText().toString();
                String aDescription = description.getText().toString();
                final boolean aAvailability = availability.isChecked();
                float aRating = rating.getRating();

                if (aName.isEmpty() || aAddress.isEmpty()) {
                    return;
                }

                mDocRef = FirebaseFirestore.getInstance().document("washrooms/"+aName);

                Map<String, Object> data = new HashMap<>();
//                data.put(NAME_KEY, aName);
                data.put(ADDRESS_KEY, aAddress);
                data.put(CITY_KEY, aCity);
                data.put(COUNTRY_KEY, aCountry);
                data.put(DESCRIPTION_KEY, aDescription);
                data.put(AVAILABILITY_KEY, aAvailability);
                data.put(RATING_KEY, aRating);

                mDocRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            makeText(WashroomData.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                        }
                        else {
                            makeText(WashroomData.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addName = name.getText().toString();
                String addAddress = address.getText().toString();

                if (addName.isEmpty() || addAddress.isEmpty()) {
                    return;
                }

                db.collection("washrooms").document(addName).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            makeText(WashroomData.this, "Washroom Data Deleted.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            makeText(WashroomData.this, "Error ! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }

    public void home(View view) {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
    }
}