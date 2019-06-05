package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
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
        starterViewHolder.textViewIDValue.setText(""+Integer.toString(mData.get(i).getIdTicket()));
        starterViewHolder.textViewPriceTicket.setText("Precio : " + Float.toString(mData.get(i).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class StarterViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIDValue;
        TextView textViewPriceTicket;

        public StarterViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIDValue = itemView.findViewById(R.id.ticket_id);
            textViewPriceTicket = itemView.findViewById(R.id.ticket_precio);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    SetIdAsyncTask setIdAsyncTask = new SetIdAsyncTask(mData.get(getAdapterPosition()).getIdTicket());
                    setIdAsyncTask.execute();

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    LayoutInflater mInflater2 = LayoutInflater.from(mContext);
                    @SuppressLint("InflateParams") final View customLayout = mInflater2.inflate(R.layout.ticket_info_layout, null);
                    TextView ticketInfo = customLayout.findViewById(R.id.textViewAlertDialogTicketInfo);

                        System.out.println(mData.get(getAdapterPosition()).getIdTicket());
                }
            });
        }
        @SuppressLint("StaticFieldLeak")
        class SetIdAsyncTask extends AsyncTask<String, Void, String> {

            Socket sk;
            PublicKey publicKey;
            DataInputStream dis;
            DataOutputStream dos;
            ObjectInputStream ois;
            int idTicket;



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            public SetIdAsyncTask(int idTicket) {

                this.idTicket = idTicket;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //dialog.dismiss();
            }

            @Override
            protected String doInBackground(String... strings) {

                try {
                    Connection connection = new Connection();
                    String ip = connection.getIp();
                    sk = new Socket(ip, 20002);
                    System.out.println("Establecida la conexión con " + ip);
                    dis = new DataInputStream(sk.getInputStream());
                    dos = new DataOutputStream(sk.getOutputStream());
                    ois = new ObjectInputStream(sk.getInputStream());
                    publicKey = (PublicKey) ois.readObject();
                    dos.writeInt(7);
                    dos.writeInt(idTicket);
                    //dis.readUTF();

                    sk.close();
                    dis.close();
                    dos.close();
                    ois.close();

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

                return null;


            }
        }
    }
}
