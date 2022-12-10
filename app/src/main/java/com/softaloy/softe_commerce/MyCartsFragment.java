package com.softaloy.softe_commerce;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.softaloy.softe_commerce.activities.PlaceOrderActivity;
import com.softaloy.softe_commerce.adapters.MyCartAdapter;
import com.softaloy.softe_commerce.models.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {


    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> list;
    ProgressBar progressBar;
    AppCompatButton buy_now;

    public MyCartsFragment() {

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.cart_recyclerview);
        buy_now = root.findViewById(R.id.buy_now);

        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        overTotalAmount = root.findViewById(R.id.textView5);

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMassagereceiver,new IntentFilter("MyTotalAmount"));

        list = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(),list);

        recyclerView.setAdapter(myCartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("addTocart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){

                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                                String documentId = documentSnapshot.getId();

                                MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);

                                myCartModel.setDocumentId(documentId);
                                list.add(myCartModel);
                                myCartAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), PlaceOrderActivity.class);
                intent.putExtra("itemlist", (Serializable) list);
                startActivity(intent);
            }
        });

        return root;
    }

    public BroadcastReceiver mMassagereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalAmount",0);
            overTotalAmount.setText("Total Bill :"+totalBill+"$");

        }
    };
}