package com.example.assignment1_fit2081_32781555.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment1_fit2081_32781555.Activities.EventGoogleResult;
import com.example.assignment1_fit2081_32781555.Activities.GoogleMapActivity;
import com.example.assignment1_fit2081_32781555.Objects.Event;
import java.util.ArrayList;
import com.example.assignment1_fit2081_32781555.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private static final ArrayList<Event>eventList = new ArrayList<>();
    public EventAdapter() {
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvEventID.setText(event.getEventID());
        holder.tvEventName.setText(event.getEventName());
        holder.tvCategoryID.setText(event.getCategoryID() + " - Category" );
        holder.tvTicketsAvail.setText(String.valueOf(event.getTicketNumber()));
        holder.tvIsActive.setText(String.valueOf(event.isActive()));

        holder.cardView.setOnClickListener(v -> {
            String eventName = event.getEventName();
            Context context  = holder.cardView.getContext();
            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("eventName",eventName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<Event> arrayList){
        eventList.clear();
        eventList.addAll(arrayList);
        notifyDataSetChanged();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView tvEventID;
        TextView tvCategoryID;
        TextView tvEventName;
        TextView tvTicketsAvail;
        TextView tvIsActive;
        View cardView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            tvEventID = itemView.findViewById(R.id.tvEventID);
            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvCategoryID = itemView.findViewById(R.id.tvCategoryID);
            tvTicketsAvail = itemView.findViewById(R.id.tvTicketNo);
            tvIsActive = itemView.findViewById(R.id.tvIsActive);
        }
    }

    @Override
    public long getItemId(int position) {
        return eventList.get(position).getEventID().hashCode();
    }

}
