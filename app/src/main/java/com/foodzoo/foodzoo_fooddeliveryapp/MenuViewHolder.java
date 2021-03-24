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

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;
    private ItemClickListener itemClickListener;
    private static final String TAG = "MenuViewHolder";

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName = (TextView)itemView.findViewById(R.id.menu_name);
        imageView=(ImageView)itemView.findViewById(R.id.menu_image);
        
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        int position = getAdapterPosition()+1;
        Log.d(TAG,"hELLO .."+position);
        //Toast.makeText(MenuViewHolder.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
        //view.getContext().startActivity();
        //Toast.makeText(view.getContext(), "Item Clicked", Toast.LENGTH_SHORT).show();

        Intent foodList =new Intent(view.getContext(),FoodList.class);
      //Category id is Key

        String final_pos="";
        if(position<=9)
        {

            final_pos="0"+position;
                 }
        else
        {
            final_pos=String.valueOf(position);
        }


        foodList.putExtra("CategoryId",final_pos);
        view.getContext().startActivity(foodList);
        //startActivity(foodList);
    }
}