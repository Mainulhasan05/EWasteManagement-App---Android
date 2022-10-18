package com.example.ewastemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class CompanyActivity extends AppCompatActivity {
    CardView openMapCard,signoutCard,upcomingEventsCard,createEventCard,createViewOrderCard;
    TextView welcomeTV,welcomeTV1;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference =firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                welcomeTV.setText(value.getString("name"));
                welcomeTV1.setText(value.getString("email"));
            }
        });

        initialize();
        setListeners();
    }

    public void initialize(){
        welcomeTV=findViewById(R.id.welcomeTV);
        welcomeTV1=findViewById(R.id.welcomeTV1);
        openMapCard=findViewById(R.id.openMapCard);
        signoutCard=findViewById(R.id.signoutCard);
        upcomingEventsCard=findViewById(R.id.upcomingEventsCard);
        createEventCard=findViewById(R.id.createEventCard);
        createViewOrderCard=findViewById(R.id.createViewOrderCard);
    }

    public void setListeners(){
        openMapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MapView.class));
            }
        });
        createViewOrderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewOrdersCompanyActivity.class));
            }
        });

        signoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),SigninActivity.class));
                finish();

            }
        });

        createEventCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CompanyCreateEventActivity.class));
            }
        });

        upcomingEventsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewUpcomingEventsActivity.class));
            }
        });
    }
}