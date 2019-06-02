package com.example.client_restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MenuStarterAdapter extends RecyclerView.Adapter<MenuStarterAdapter.StarterViewHolder> {


    private Context mContext;
    private List<Dish> mData;

    public MenuStarterAdapter(Context mContext, List<Dish> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public StarterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_dish, viewGroup ,false);

        return new StarterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StarterViewHolder starterViewHolder, int i) {

        starterViewHolder.textViewDishName.setText(mData.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class StarterViewHolder extends RecyclerView.ViewHolder{

        TextView textViewDishName;
        ImageView imageViewDishImage;

        public StarterViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDishName = itemView.findViewById(R.id.dish_name_id);
            imageViewDishImage = itemView.findViewById(R.id.dish_img_id);
        }
    }
}
