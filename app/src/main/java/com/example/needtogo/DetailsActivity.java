package com.example.needtogo;

import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import android.view.View;
import android.widget.TextView;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle b = getIntent().getExtras();
        String documentPath = b.getString("document");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final String name = db.document(documentPath).getId();

        final TextView details = (TextView) findViewById(R.id.detailsText);

        db.document(documentPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String data = document.getData().toString();
                        String address = document.getString("address");
                        String city = document.getString("city");
                        String country = document.getString("country");
                        String description = document.getString("description");
                        boolean availl = document.getBoolean("availability");
                        String avail = "";
                        if (availl) {
                            avail = "Available";
                        }
                        else {
                            avail = "Not Available";
                        }
                        double rating = document.getDouble("rating");
                        details.setText("Name: " + name + "\nAddress: " + address + "\nCity: " + city + "\nCountry: " + country + "\nDescription: " + description + "\nAvailability: " + avail + "\nRating: " + rating);
                        setTitle(document.getId());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "Get failed with ", task.getException());
                }
            }
        });

    }

    public void home(View view) {
        startActivity(new Intent(getApplicationContext(), MainPage.class));
    }
}