package com.example.client_restaurant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuStarterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuStarterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuStarterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuStarterFragment() {
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
    public static MenuStarterFragment newInstance(String param1, String param2) {
        MenuStarterFragment fragment = new MenuStarterFragment();
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

        View v = inflater.inflate(R.layout.fragment_menu_starter, container, false);

        //Aquí debería pasarle
        List<Dish> dishList = new ArrayList<>();

        dishList.add(new Dish("Macarrones","Macarroneeee",R.drawable.ic_bill));
        dishList.add(new Dish("efwefwe","Macarroneeee",R.drawable.ic_bill));
        dishList.add(new Dish("Macfearrones","Macarroneeee",R.drawable.ic_bill));
        dishList.add(new Dish("Mnes","Macarroneeee",R.drawable.ic_bill));
        dishList.add(new Dish("Macafwefwrrones","Macarroneeee",R.drawable.ic_bill));
        dishList.add(new Dish("fwerrones","Macarroneeee",R.drawable.ic_bill));
        dishList.add(new Dish("Mfwfrrones","Macarroneeee",R.drawable.ic_bill));


        RecyclerView recyclerViewDish = v.findViewById(R.id.recyclerview_menu_starter_id);
        MenuStarterAdapter recyclerViewAdapterDish = new MenuStarterAdapter(getContext(),dishList);
        recyclerViewDish.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerViewDish.setAdapter(recyclerViewAdapterDish);

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
}
