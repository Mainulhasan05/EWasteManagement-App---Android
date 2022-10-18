package com.example.ewastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewOrdersActivity extends AppCompatActivity implements OrdersViewInterface{
ArrayList<PostModel> list;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private Object OrdersViewInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.recyclerViewOrders);
        list=new ArrayList<>();
        firebaseFirestore.collection("posts")
                .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot result=task.getResult();
                            for(DocumentSnapshot x: result){
                                String email=x.getString("email");
                                String pickupdate=x.getString("pickupdate");
                                String pickuptime=x.getString("pickuptime");
                                String description=x.getString("notes");
                                String place=x.getString("address");
                                String waste=x.getString("waste");
                                String reward=x.getString("rewardType");
                                String imageurl=x.getString("imageurl");
                                PostModel obj=new PostModel(email,waste,pickupdate,pickuptime,place,description,reward,imageurl);
                                list.add(obj);
                            }
                            setAdapter();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast(e.getMessage());
                    }
                });
    }
    public void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    public void setAdapter(){
        OrdersRecycleAdapter recycleAdapter=new OrdersRecycleAdapter(list,getApplicationContext(), (com.example.ewastemanagement.OrdersViewInterface) this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recycleAdapter);
    }
    public void onItemClick(int position) {
        Intent intent=new Intent(getApplicationContext(),DetailOrderActivity.class);
        intent.putExtra("email",list.get(position).getEmail());
        intent.putExtra("place",list.get(position).getAddress());
        intent.putExtra("wasteType",list.get(position).getWaste());
        intent.putExtra("rewardType",list.get(position).getRewardType());
        intent.putExtra("date",list.get(position).getPickupdate());
        intent.putExtra("time",list.get(position).getPickuptime());

        startActivity(intent);
    }
}