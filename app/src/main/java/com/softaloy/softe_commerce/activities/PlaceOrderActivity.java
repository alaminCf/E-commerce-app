package com.softaloy.softe_commerce.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.models.MyCartModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        List<MyCartModel> list = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemlist");

        if (list != null && list.size() >0){

            for (MyCartModel myCartModel : list){

                final HashMap<String,Object> cartmap = new HashMap<>();
                cartmap.put("productName", myCartModel.getProductName());
                cartmap.put("productPrice", myCartModel.getProductPrice());
                cartmap.put("currentDate", myCartModel.getCurrentDate());
                cartmap.put("currentTime", myCartModel.getCurrentTime());
                cartmap.put("totalquality", myCartModel.getTotalquality());
                cartmap.put("totalPrice", myCartModel.getTotalPrice());

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrdetr").add(cartmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PlaceOrderActivity.this, "Your Order Has Been Placed", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        });

            }

        }
    }
}