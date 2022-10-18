package com.example.ewastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailOrderActivity extends AppCompatActivity {
TextView emailTV,wastePlace,wasteReturnReward,wasteTv,wastePickupTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        Intent i=getIntent();

        wasteTv=findViewById(R.id.wasteType);
        wastePickupTime=findViewById(R.id.wastePickupTime);
        wasteReturnReward=findViewById(R.id.wasteReturnReward);
        wastePlace=findViewById(R.id.wastePlace);

        wasteTv.setText(i.getStringExtra("wasteType"));
        wastePickupTime.setText(i.getStringExtra("date")+" "+i.getStringExtra("time"));
        wasteReturnReward.setText(i.getStringExtra("rewardType"));
        wastePlace.setText(i.getStringExtra("place"));
    }
}