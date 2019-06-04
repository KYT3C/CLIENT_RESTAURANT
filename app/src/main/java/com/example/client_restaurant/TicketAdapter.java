package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StarterViewHolder starterViewHolder, int i) {
        starterViewHolder.textViewIdValue.setText(Integer.toString(mData.get(i).getIdTicket()));
        starterViewHolder.textViewPriceTicket.setText("Precio : " + Float.toString(mData.get(i).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class StarterViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIDTable;
        TextView textViewPriceTicket;
        TextView textViewIdValue;

        public StarterViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIDTable = itemView.findViewById(R.id.ticket_id);
            textViewPriceTicket = itemView.findViewById(R.id.ticket_precio);
            textViewIdValue = itemView.findViewById(R.id.textViewIdValue);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    LayoutInflater mInflater2 = LayoutInflater.from(mContext);
                    @SuppressLint("InflateParams") final View customLayout = mInflater2.inflate(R.layout.ticket_info_layout, null);
                    TextView ticketInfo = customLayout.findViewById(R.id.textViewAlertDialogTicketInfo);
                    try {
                        Connection connection = new Connection();
                        String ip = connection.getIp();
                        Socket sk = new Socket(ip, 20002);
                        System.out.println("Establecida la conexión con " + ip);
                        DataInputStream dis = new DataInputStream(sk.getInputStream());
                        DataOutputStream dos = new DataOutputStream(sk.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(sk.getInputStream());
                        PublicKey publicKey = (PublicKey) ois.readObject();
                        dos.writeInt(7);


                        dos.writeInt(mData.get(getAdapterPosition()).getIdTicket());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }





                }
            });
        }
    }
}
