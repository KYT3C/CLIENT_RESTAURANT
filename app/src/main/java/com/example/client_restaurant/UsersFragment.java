package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Users> ticketList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MenuStarterFragment.OnFragmentInteractionListener mListener;

    public UsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersFragment newInstance(String param1, String param2) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    UsersAdapter recyclerViewAdapterTickets;
    RecyclerView recyclerViewTickets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerViewTickets = v.findViewById(R.id.recyclerview_users);
        UsersAdapter recyclerViewAdapterDish = new UsersAdapter(getContext(), ticketList);
        recyclerViewTickets.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerViewTickets.setAdapter(recyclerViewAdapterDish);

        UsersFragment.GetTicketAsyncTask getDishAsyncTask = new GetTicketAsyncTask(1);
        getDishAsyncTask.execute();

        FloatingActionButton fab = v.findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                LayoutInflater mInflater2 = LayoutInflater.from(getContext());
                View customLayout = mInflater2.inflate(R.layout.user_add_layout, null);

                alertDialog.setView(customLayout);

                final AlertDialog aaaa = alertDialog.create();

                TextView dni = customLayout.findViewById(R.id.editTextDishName);
                TextView name = customLayout.findViewById(R.id.editTextDishPrice);
                TextView apellidos = customLayout.findViewById(R.id.editTextDishStock);
                TextView telefono = customLayout.findViewById(R.id.editTextDishDniKitchen);
                TextView pass = customLayout.findViewById(R.id.editTextDishDescription);
                RadioGroup selec = customLayout.findViewById(R.id.radioGroup);

                int kind=1;

                if(selec == customLayout.findViewById(R.id.rbCamarero))
                    kind = 3;
                if(selec == customLayout.findViewById(R.id.rbCocinero))
                    kind = 2;
                if(selec == customLayout.findViewById(R.id.rbAdmin))
                    kind = 4;

                Button btn = customLayout.findViewById(R.id.btnInsert);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                aaaa.show();
            }


        });


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @SuppressLint("StaticFieldLeak")
    class GetTicketAsyncTask extends AsyncTask<String, Void, String> {

        Socket sk;
        DataInputStream dis;
        DataOutputStream dos;
        ObjectInputStream ois;
        PublicKey publicKey;
        List<Users> ticketList2 = new ArrayList<Users>();
        int option =1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ticketList = ticketList2;
            recyclerViewAdapterTickets = new UsersAdapter(getContext(),ticketList);
            recyclerViewTickets.setLayoutManager(new GridLayoutManager(getContext(),3));
            recyclerViewTickets.setAdapter(recyclerViewAdapterTickets);

        }

        private GetTicketAsyncTask(int option){
            this.option = option;
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

                if(option ==1) {
                    dos.writeInt(12);

                    int size = dis.readInt();
                    System.out.println("TAMAÑO LISTA : " + size);

                    for (int i = 0; i < size; i++) {

                        String dni = dis.readUTF();
                        String firstName = dis.readUTF();
                        String surnames = dis.readUTF();
                        String phoneNumber = dis.readUTF();
                        int kind = dis.readInt();

                        ticketList2.add(new Users(dni, firstName, surnames, phoneNumber, kind));
                        System.out.println("TAMAÑO LISTA BUCLE: " + ticketList2.size());
                    }
                }
                else if(option == 2){

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            return null;

        }

    }


}

