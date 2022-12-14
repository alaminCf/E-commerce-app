package com.softaloy.softe_commerce.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.softaloy.softe_commerce.R;
import com.softaloy.softe_commerce.adapters.HomeAdapter;
import com.softaloy.softe_commerce.adapters.PopularAdapters;
import com.softaloy.softe_commerce.adapters.RecommendedAdapter;
import com.softaloy.softe_commerce.adapters.ViewAllAdapter;
import com.softaloy.softe_commerce.models.HomeCategory;
import com.softaloy.softe_commerce.models.PopularModle;
import com.softaloy.softe_commerce.models.RecommendedModle;
import com.softaloy.softe_commerce.models.ViewAllModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressbar;
    RecyclerView popularRec, homeCatRec, recommendedRec;
    FirebaseFirestore db;

    ///popular items..........................
    List<PopularModle> popularModleList;
    PopularAdapters popularAdapters;

    /////////////search view.....
    EditText search_box;
   private List<ViewAllModel> viewAllModelList;
   private RecyclerView recyclerViewSearch;
   private ViewAllAdapter viewAllAdapter;


    /////// Home Category.......
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    /////// Recommended........
    List<RecommendedModle> list;
    RecommendedAdapter recommendedAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        scrollView = root.findViewById(R.id.scroll_view);
        progressbar = root.findViewById(R.id.progressbar);

        progressbar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        /////popular items......
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModleList = new ArrayList<>();
        popularAdapters= new PopularAdapters(getActivity(),popularModleList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModle popularModle = document.toObject(PopularModle.class);
                                popularModleList.add(popularModle);
                                popularAdapters.notifyDataSetChanged();

                                progressbar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        ////Home Category......
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter= new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        ////Recommended ......

        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        list = new ArrayList<>();
        recommendedAdapter= new RecommendedAdapter(getActivity(),list);
        recommendedRec.setAdapter(recommendedAdapter);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModle recommendedModle = document.toObject(RecommendedModle.class);
                                list.add(recommendedModle);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {

                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        /////////////search view.....
        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);
        viewAllModelList = new ArrayList<>();
         viewAllAdapter = new ViewAllAdapter(getContext(), viewAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()){

                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }else {

                    searchProduct(s.toString());
                }

            }
        });


        return root;
    }

    private void searchProduct(String type) {

        if (!type.isEmpty()){

            db.collection("All Products").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful() && task.getResult() !=null){
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }

    }
}