package com.foodzoo.foodzoo_fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.foodzoo.Interface.ItemClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId="";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");

        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        if (!categoryId.isEmpty() && categoryId != null)
        {
//code changes here
            loadListFood();
        }


    }

    //video no 30 updated firebase Ui.

    private void loadListFood() {

        //code changes here orderbychild("MenuId")
        FirebaseRecyclerOptions<Food> options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(foodList.orderByChild("MenuId").equalTo(categoryId),Food.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder, int i, @NonNull Food model) {
//code changes here
                        viewHolder.food_name.setText(model.getName());
                        //Picasso.with(getBaseContext()).load(model.getImage)).into(foodViewHolder.food_image);
                        Picasso.with(getBaseContext()).load(model.getImage())
                                .into(viewHolder.food_image);
                       // Toast.makeText(FoodList.this,model.getMenuId(),Toast.LENGTH_SHORT).show();




//code changes here
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                    }
                });

            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.food_item,parent,false);

                return new FoodViewHolder(itemView);
            }
        };
        adapter.startListening();

        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //adapter.startListening();
    }
}
