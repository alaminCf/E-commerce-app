package com.softaloy.softe_commerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.models.ViewAllModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailed_img, add_item, remove_item;
    TextView price, rating, description, quantity;
    int totalquantity = 1;
    int totalprice =0;
    AppCompatButton add_to_cart;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ViewAllModel viewAllModel = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");

        if (object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;

        }


        quantity = findViewById(R.id.quantity);
        detailed_img = findViewById(R.id.detailed_img);
        add_item = findViewById(R.id.add_item);
        remove_item = findViewById(R.id.remove_item);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_desc);
        add_to_cart = findViewById(R.id.add_to_cart);

        if (viewAllModel != null);{
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailed_img);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("price :$"+viewAllModel.getPrice()+"/kg");

            totalprice = viewAllModel.getPrice() * totalquantity;

            if (viewAllModel.getType().equals("egg")){
                price.setText("price :$"+viewAllModel.getPrice()+"/Dozen");

                totalprice = viewAllModel.getPrice() * totalquantity;
            }

            if (viewAllModel.getType().equals("milk")){
                price.setText("price :$"+viewAllModel.getPrice()+"/litre");

                totalprice = viewAllModel.getPrice() * totalquantity;
            }

        }
        
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedTocart();
            }
        });

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalquantity <10){
                    totalquantity++;
                    quantity.setText(String.valueOf(totalquantity));

                    totalprice = viewAllModel.getPrice() * totalquantity;
                }
            }
        });

        remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalquantity > 1){
                    totalquantity--;
                    quantity.setText(String.valueOf(totalquantity));

                    totalprice = viewAllModel.getPrice() * totalquantity;
                }

            }
        });

    }

    private void addedTocart() {

        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("mm dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh,mm, ss, a");
        saveCurrentTime= currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartmap = new HashMap<>();
        cartmap.put("productName", viewAllModel.getName());
        cartmap.put("productPrice", price.getText().toString());
        cartmap.put("currentDate", saveCurrentDate);
        cartmap.put("currentTime", saveCurrentTime);
        cartmap.put("totalquality", quantity.getText().toString());
        cartmap.put("totalPrice", totalprice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("addTocart").add(cartmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Add To Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                });
    }
}