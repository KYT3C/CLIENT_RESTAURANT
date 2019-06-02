package com.example.client_restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.StarterViewHolder> {


    private Context mContext;
    private List<Ticket> mData;

    public TicketAdapter(Context mContext, List<Ticket> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public StarterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_tickets, viewGroup ,false);

        return new StarterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarterViewHolder starterViewHolder, int i) {

        starterViewHolder.textViewIDTable.setText(mData.toString());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class StarterViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIDTable;
        TextView textViewPriceTicket;

        public StarterViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIDTable = itemView.findViewById(R.id.ticket_id);
            textViewPriceTicket = itemView.findViewById(R.id.ticket_precio);
        }
    }
}
