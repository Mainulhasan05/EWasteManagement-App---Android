package com.example.ewastemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.MyEventViewHolder> {
    ArrayList<EventModel> list;
    Context context;

    public EventRecycleAdapter(Context context,ArrayList<EventModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.event_list_view,parent,false);
        return new MyEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventViewHolder holder, int position) {
        holder.eventName.setText(list.get(position).getEventname());
        holder.eventPlace.setText(list.get(position).getPlace());
        holder.eventDescription.setText(list.get(position).getDescription());
        holder.eventDate.setText(list.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyEventViewHolder extends RecyclerView.ViewHolder{
        TextView eventName,eventPlace,eventDate,eventTime,eventDescription;
        public MyEventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName=itemView.findViewById(R.id.recycleEventName);
            eventPlace=itemView.findViewById(R.id.recycleEventPlace);
            eventDescription=itemView.findViewById(R.id.recycleEventDescription);
            eventDate=itemView.findViewById(R.id.recycleEventDate);
        }
    }
}
