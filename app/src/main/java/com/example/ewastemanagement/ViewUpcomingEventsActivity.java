package com.example.ewastemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewUpcomingEventsActivity extends AppCompatActivity {
ArrayList<EventModel> list;
RecyclerView recyclerView;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_upcoming_events);
        recyclerView=findViewById(R.id.recyclerView);
        firebaseFirestore=FirebaseFirestore.getInstance();
        CollectionReference collectionReference=  firebaseFirestore.collection("events")
                .document("OUGzYy9sgEhlOrSDDsJgtn8HmsI3").collection("private");

        list=new ArrayList<>();

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot result=task.getResult();
                    for(DocumentSnapshot x:result){
                        String name=x.getString("eventname");
                        String place=x.getString("place");
                        String description=x.getString("description");
                        String date=x.getString("date");
                        String time=x.getString("time");
                        EventModel obj=new EventModel(name,place,date,time,description);
                        list.add(obj);
                    }
//                    Toast.makeText(getApplicationContext(),list.size()+"",Toast.LENGTH_SHORT).show();
                    EventRecycleAdapter adapter=new EventRecycleAdapter(getApplicationContext(),list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                }

            }
        });


    }
}