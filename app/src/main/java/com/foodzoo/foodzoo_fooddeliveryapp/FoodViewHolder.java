package com.foodzoo.foodzoo_fooddeliveryapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodzoo.Interface.ItemClickListener;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView food_name;
    public ImageView food_image;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        food_name = (TextView)itemView.findViewById(R.id.food_name);
        food_image=(ImageView)itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


//code changes here
        Intent foodDetails = new Intent(view.getContext(),FoodDetails.class);
        foodDetails.putExtra("FoodId", food_name.getText().toString());
        view.getContext().startActivity(foodDetails);

    }

}
