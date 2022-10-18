package com.example.ewastemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeUser extends AppCompatActivity {
CardView openMapCard,signoutCard,upcomingEventsCard,createPostCard,viewPostCard;
TextView welcomeTV,welcomeTV1;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
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
        createPostCard=findViewById(R.id.createPostCard);
        viewPostCard=findViewById(R.id.viewPostCard);
    }
    public void setListeners(){
        openMapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MapView.class));
            }
        });
        viewPostCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ViewOrdersActivity.class));
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

        createPostCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreatePostActivity.class));
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