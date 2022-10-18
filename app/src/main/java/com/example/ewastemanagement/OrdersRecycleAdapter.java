package com.example.ewastemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdersRecycleAdapter extends RecyclerView.Adapter<OrdersRecycleAdapter.MyOrderViewHolder> {
    ArrayList<PostModel> list;
    OrdersViewInterface ordersViewInterface;
    Context context;

    public OrdersRecycleAdapter(ArrayList<PostModel> list, Context context,OrdersViewInterface ordersViewInterface) {
        this.list = list;
        this.context = context;
        this.ordersViewInterface=ordersViewInterface;
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.order_row,parent,false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        holder.customerName.setText(list.get(position).getEmail());
        holder.wasteType.setText(list.get(position).getWaste());
        holder.pickupDate.setText(list.get(position).getRewardType());
        holder.place.setText(list.get(position).getAddress());
        holder.pickupTIme.setText(list.get(position).getPickupdate()+" "+list.get(position).getPickuptime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyOrderViewHolder extends RecyclerView.ViewHolder{
        TextView customerName,wasteType,pickupDate,place,pickupTIme;
        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName=itemView.findViewById(R.id.recycleCustomerName);
            wasteType=itemView.findViewById(R.id.recycleWasteType);
            pickupDate=itemView.findViewById(R.id.recyclePickupDate);
            place=itemView.findViewById(R.id.recycleWastePlace);
            pickupTIme=itemView.findViewById(R.id.recylePickupTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ordersViewInterface!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            ordersViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
