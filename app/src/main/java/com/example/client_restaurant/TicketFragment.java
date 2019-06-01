package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class TicketFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Ticket> ticketList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MenuStarterFragment.OnFragmentInteractionListener mListener;

    public TicketFragment() {
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
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
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


    MenuStarterAdapter recyclerViewAdapterDish;
    RecyclerView recyclerViewDish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tickets, container, false);
        recyclerViewDish = v.findViewById(R.id.recyclerview_tickets);
        //MenuStarterAdapter recyclerViewAdapterDish = new MenuStarterAdapter(getContext(),dishList);
        //recyclerViewDish.setLayoutManager(new GridLayoutManager(getContext(),3));
        //recyclerViewDish.setAdapter(recyclerViewAdapterDish);

        TicketFragment.GetDishAsyncTask getDishAsyncTask = new TicketFragment.GetDishAsyncTask();
        getDishAsyncTask.execute();

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
    class GetDishAsyncTask extends AsyncTask<String, Void, String> {

        Socket sk;
        DataInputStream dis;
        DataOutputStream dos;
        ObjectInputStream ois;
        PublicKey publicKey;
        List<Ticket> ticketList2 = new ArrayList<Ticket>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ticketList = ticketList2;
            //recyclerViewAdapterDish = new MenuStarterAdapter(getContext(),ticketList);
            recyclerViewDish.setLayoutManager(new GridLayoutManager(getContext(),3));
            recyclerViewDish.setAdapter(recyclerViewAdapterDish);

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String ip = "192.168.137.1";
                sk = new Socket(ip, 20002);
                System.out.println("Establecida la conexión con " + ip);
                dis = new DataInputStream(sk.getInputStream());
                dos = new DataOutputStream(sk.getOutputStream());
                ois = new ObjectInputStream(sk.getInputStream());

                publicKey = (PublicKey) ois.readObject();

                dos.writeInt(2);

                int size = dis.readInt();
                System.out.println("TAMAÑO LISTA : " + size);

                for (int i = 0; i < size; i++) {

                    String dishName = dis.readUTF();
                    int idItemDish = dis.readInt();
                    float price = dis.readFloat();
                    int quantityStock = dis.readInt();
                    int statusDish = dis.readInt();
                    String descriptionDish = dis.readUTF();
                    String dniKitchen = dis.readUTF();

                    System.out.println("NOMBRE DEL PLATO: " + dishName);

                    //dishList2.add(new Dish(dishName,idItemDish,price,quantityStock,statusDish,descriptionDish,dniKitchen));
                    //System.out.println("TAMAÑO LISTA BUCLE: " + dishList2.size());
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

