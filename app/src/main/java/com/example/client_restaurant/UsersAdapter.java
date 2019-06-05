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
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.StarterViewHolder> {


    private Context mContext;
    private List<Users> mData;


    public UsersAdapter(Context mContext, List<Users> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public StarterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_users, viewGroup ,false);

        return new StarterViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StarterViewHolder starterViewHolder, int i) {
        starterViewHolder.textViewIDValue.setText(mData.get(i).getFirstName());
        starterViewHolder.textViewPriceTicket.setText(mData.get(i).getDni());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class StarterViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIDValue;
        TextView textViewPriceTicket;
        AlertDialog.Builder alertDialog = null;

        public StarterViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewIDValue = itemView.findViewById(R.id.ticket_id);
            textViewPriceTicket = itemView.findViewById(R.id.ticket_precio);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {


//                    alertDialog = new AlertDialog.Builder(mContext);
//                    LayoutInflater mInflater2 = LayoutInflater.from(mContext);
//                    @SuppressLint("InflateParams") final View customLayout = mInflater2.inflate(R.layout.ticket_info_layout, null);
//                    TextView ticketInfo = customLayout.findViewById(R.id.textViewAlertDialogTicketInfo);
//                    alertDialog.setView(customLayout);

                    String dni = mData.get(getAdapterPosition()).getDni();
                    SetIdAsyncTask setIdAsyncTask = new SetIdAsyncTask(dni,1);
                    setIdAsyncTask.execute();
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
            String dni, nombre, apellidos, phoneNumber;
            int kind,option;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            public SetIdAsyncTask(String dni,int option) {

                this.dni = dni;
                this.option = option;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(option == 1) {
                    String total = "";
                    total = total + "Nombre : " + nombre;
                    total = total + "\nApellidos :" + apellidos;
                    total = total + "\nDNI : " + dni;
                    total = total + "\nNumero de Telefono : " + phoneNumber;
                    total = total + "\ntipo de empleado :" + kind;

                    alertDialog = new AlertDialog.Builder(mContext);
                    LayoutInflater mInflater2 = LayoutInflater.from(mContext);
                    @SuppressLint("InflateParams") final View customLayout = mInflater2.inflate(R.layout.ticket_info_layout, null);
                    TextView ticketInfo = customLayout.findViewById(R.id.textViewAlertDialogTicketInfo);
                    ticketInfo.setText(total);
                    alertDialog.setView(customLayout);
                    final AlertDialog alert = alertDialog.create();
                    Button btn = customLayout.findViewById(R.id.btnDelete);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SetIdAsyncTask siat = new SetIdAsyncTask(dni, 2);
                            siat.execute();
                            alert.cancel();
                        }
                    });

                    alert.show();

                }
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

                    if(option == 1) {
                        dos.writeInt(17);
                        dos.writeUTF(dni);
                        nombre = dis.readUTF();
                        apellidos = dis.readUTF();
                        phoneNumber = dis.readUTF();
                        kind = dis.readInt();
                    }else if (option == 2){
                        dos.writeInt(13);
                        dos.writeUTF(dni);

                    }


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
