package com.example.client_restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StarterViewHolder starterViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class StarterViewHolder extends RecyclerView.ViewHolder{

        public StarterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
