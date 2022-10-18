package com.example.ewastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CompanyCreateEventActivity extends AppCompatActivity {
    private EditText inputEventName,inputEventPlace,inputEventDate,inputEventTime,inputEventDescription;
    private Button eventSaveBtn;
    String eventName,eventPlace,eventDate,eventTime,eventDescription;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ProgressBar eventProgressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_create_event);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        initialize();
        setListeners();
    }
    public void initialize(){
        inputEventName=findViewById(R.id.inputEventName);
        inputEventPlace=findViewById(R.id.inputEventPlace);
        inputEventDate=findViewById(R.id.inputEventDate);
        inputEventTime=findViewById(R.id.inputEventTime);
        eventSaveBtn=findViewById(R.id.eventSaveBtn);
        inputEventDescription=findViewById(R.id.inputEventDescription);
        eventProgressbar=findViewById(R.id.eventProgressbar);
    }
    public void loading(boolean load){
        if(load){
            eventProgressbar.setVisibility(View.VISIBLE);
            eventSaveBtn.setVisibility(View.INVISIBLE);
        }
        else{
            eventSaveBtn.setVisibility(View.VISIBLE);
            eventProgressbar.setVisibility(View.INVISIBLE);
        }
    }
    public void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    public void setListeners(){
        eventSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading(true);
                eventName=inputEventName.getText().toString();
                eventPlace=inputEventPlace.getText().toString();
                eventDate=inputEventDate.getText().toString();
                eventTime=inputEventTime.getText().toString();
                eventDescription=inputEventDescription.getText().toString();
                boolean error=false;
                if(eventDate==null){
                    inputEventDate.setError("Date is required");
                    error=true;
                }
                if(eventTime==null){
                    inputEventTime.setError("Time is required");
                    error=true;
                }
                if(!error){
                    EventModel eventModel=new EventModel(eventName,eventPlace,eventDate,eventTime,eventDescription);
                    firebaseFirestore.collection("events")
                            .document(firebaseUser.getUid())
                            .collection("private").add(eventModel)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        showToast("Data Saved Successfully");
                                        loading(false);

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                clearForm();
                                            }
                                        },1000);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        });
    }

    public void clearForm(){
        inputEventName.setText("");
        inputEventDate.setText("");
        inputEventTime.setText("");
        inputEventDescription.setText("");
        inputEventPlace.setText("");


    }
}