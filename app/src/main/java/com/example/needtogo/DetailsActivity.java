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

        System.out.println(db.document(documentPath).getId());

        final TextView details = (TextView) findViewById(R.id.detailsText);


        db.document(documentPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        details.setText(document.getData().toString());
                        setTitle(document.getId());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}