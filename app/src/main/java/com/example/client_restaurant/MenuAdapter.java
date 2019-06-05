package com.example.client_restaurant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    Context mContext;
    List<Item> mData;
    int selected_position = 0;
    String data;



    public MenuAdapter(Context mContext, List<Item> mData, String data) {

        this.mContext = mContext;
        this.mData = mData;
        this.data = data;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout,viewGroup,false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i) {

        menuViewHolder.imgIcon.setImageResource(mData.get(i).getIcon());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder{

        Fragment menuFragment = new MenuFragment();
        Fragment timerFragment = new TimerFragment();
        Fragment settingsFragment = new SettingsFragment();
        Fragment ticketFragment = new TicketFragment();
        Fragment profileFragment = new ProfileFragment();
        Fragment usersFragment = new UsersFragment();
        Fragment dinnerTableFragment = new DinnerTableFragment();

        Bundle args = new Bundle();

        ImageView imgIcon;

        public  MenuViewHolder(@NonNull final View itemView){
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);
                }
            });
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if (getAdapterPosition() == 0) {
                       AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

                       activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).addToBackStack(null).commit();
                   }
                   if (getAdapterPosition() == 1) {
                       AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

                       activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, menuFragment).addToBackStack(null).commit();
                   }
                   if(getAdapterPosition() == 3){
                       AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

                       activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, dinnerTableFragment).addToBackStack(null).commit();
                   }

                   if(getAdapterPosition() == 2){
                       AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

                       activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, ticketFragment).addToBackStack(null).commit();
                   }


                   if (getAdapterPosition() == 4) {
                       AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

                       activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, timerFragment).addToBackStack(null).commit();
                   }
                   if (getAdapterPosition() == 5) {
                       AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                       activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, usersFragment).addToBackStack(null).commit();
                   }

               }
           });
        }
    }
}
