package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView nombre, tDni, telefono, kind;
    private String sNombre, sTelefono, surname;
    private int skind;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String data;
    private String dni;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
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
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dni = TempUser.Singleton().getDni();

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        nombre = v.findViewById(R.id.txtNombre);
        tDni = v.findViewById(R.id.txtDNI);
        tDni.setText(dni);
        telefono = v.findViewById(R.id.txtNumTel);
        kind = v.findViewById(R.id.txtTipo);

        GetTrabajadorActual gta = new GetTrabajadorActual();
        gta.execute();

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
    class GetTrabajadorActual extends AsyncTask<String, Void, String> {

        Socket sk;
        PublicKey publicKey;
        DataInputStream dis;
        DataOutputStream dos;
        ObjectInputStream ois;


        public GetTrabajadorActual() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            nombre.setText(sNombre+ ", "+ surname);
            telefono.setText(sTelefono);
            if (skind==1){
                kind.setText("Cliente");
            }else if (skind ==2){
                kind.setText("Kitchen");
            }else if (skind == 3){
                kind.setText("Waiter");
            }else if (skind == 4){
                kind.setText("Admin");
            }
            super.onPostExecute(s);
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
                dos.writeInt(17);
                dos.writeUTF(dni);

                sNombre= dis.readUTF();
                surname = dis.readUTF();
                sTelefono = dis.readUTF();
                skind = dis.readInt();
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
