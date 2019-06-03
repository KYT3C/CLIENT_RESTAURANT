package com.example.client_restaurant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuStarterAdapter extends RecyclerView.Adapter<MenuStarterAdapter.StarterViewHolder> {


    private Context mContext;
    private List<Dish> mData;

     MenuStarterAdapter(Context mContext, List<Dish> mData) {
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

     class StarterViewHolder extends RecyclerView.ViewHolder{


        TextView textViewDishName;
        ImageView imageViewDishImage;

         StarterViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewDishName = itemView.findViewById(R.id.dish_name_id);
            imageViewDishImage = itemView.findViewById(R.id.dish_img_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    LayoutInflater mInflater2 = LayoutInflater.from(mContext);
                    @SuppressLint("InflateParams") final View customLayout = mInflater2.inflate(R.layout.dish_info_layout, null);
                    TextView dishName = customLayout.findViewById(R.id.textViewAlertDialogDishName);
                    dishName.setText(mData.get(getAdapterPosition()).getName());
                    ImageView dishImage = customLayout.findViewById(R.id.imageViewAlertDialogDishImage);


                    TextView dishDescription = customLayout.findViewById(R.id.textViewAlertDialogDishDescription);
                    dishDescription.setText(mData.get(getAdapterPosition()).getDescriptionDish());
                    alertDialog.setView(customLayout);
                    alertDialog.create();
                    alertDialog.show();
                }
            });
        }
        private Image getDishImage(){

             Image dishImage = null;
             return dishImage;
        }
    }
}
