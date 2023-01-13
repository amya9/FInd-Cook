package com.amyaglobal.find_cook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amyaglobal.find_cook.adapters.CookSpecificationFeaturesAdapter;
import com.amyaglobal.find_cook.models.CookSpecificationFeaturesModel;

import java.util.List;

public class CookSpecificationFragment extends Fragment {
    private RecyclerView cookSpecificationRecyclerView;
    public static List<CookSpecificationFeaturesModel> cookSpecificationFeaturesModelList;


    public CookSpecificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view;
       view = inflater.inflate(R.layout.fragment_cook_specification, container, false);
       cookSpecificationRecyclerView = view.findViewById(R.id.cook_specification_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        cookSpecificationRecyclerView.setLayoutManager(linearLayoutManager);


        CookSpecificationFeaturesAdapter cookSpecificationFeaturesAdapter = new CookSpecificationFeaturesAdapter(cookSpecificationFeaturesModelList);
        cookSpecificationRecyclerView.setAdapter(cookSpecificationFeaturesAdapter);
        cookSpecificationFeaturesAdapter.notifyDataSetChanged();


        return view;
    }
}