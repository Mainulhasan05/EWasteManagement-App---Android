package com.example.ewastemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enable verbose OneSignal logging to debug issues if needed.


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            DocumentReference documentReference =firebaseFirestore.collection("users").document(firebaseUser.getUid());
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    String role="";
                    role=value.getString("role");
                    String finalRole = role;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(firebaseUser!=null && firebaseUser.isEmailVerified()){

                                if(finalRole.equals("user")){

                                    startActivity(new Intent(getApplicationContext(),HomeUser.class));
                                }
                                else{
                                    startActivity(new Intent(getApplicationContext(),CompanyActivity.class));
                                }
                            }

                            finish();
                        }
                    },600);

                }
            });

        }
        else{
            startActivity(new Intent(getApplicationContext(),SigninActivity.class));
            finish();
        }

    }
    public void onStart(){
        super.onStart();

    }
    public void onResume(){
        super.onResume();



    }
}