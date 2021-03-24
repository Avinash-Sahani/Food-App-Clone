package com.foodzoo.foodzoo_fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.foodzoo.database.Database;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";

    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        //firebase
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart =(FloatingActionButton)findViewById(R.id.btncart);

        //funtion for cart button in food details activity
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Toast.makeText(FoodDetails.this, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });

        food_description =(TextView)findViewById(R.id.food_description);
        food_name = (TextView)findViewById(R.id.food_name);
        food_price =(TextView)findViewById(R.id.food_price);

        food_image =(ImageView)findViewById(R.id.img_food);

        collapsingToolbarLayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExtendedAppbar);
        //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.id.C);


        //get foodId from intent

        if (getIntent() != null)
        {
            foodId=getIntent().getStringExtra("FoodId");

          if(foodId!=null)
            getDetailFood(foodId);
        }




    }

    private void getDetailFood(final String foodname) {
//Code Changes here


       foods.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dsp : snapshot.getChildren())
               {
                    currentFood=dsp.getValue(Food.class);

                   if(currentFood.getName().equals(foodname))
                   {
                       Picasso.with(getBaseContext()).load(currentFood.getImage())
                               .into(food_image);


                       collapsingToolbarLayout.setTitle(currentFood.getName());
                       food_price.setText(currentFood.getPrice());
                       food_name.setText(currentFood.getName());
                       food_description.setText(currentFood.getDescription());

                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}
