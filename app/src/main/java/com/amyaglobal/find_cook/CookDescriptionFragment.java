package com.amyaglobal.find_cook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CookDescriptionFragment extends Fragment {
    private TextView cookDescriptionContainer;
    public static String cookDescriptionAndOtherVariable;

    public CookDescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_cook_description, container, false);
        cookDescriptionContainer = view.findViewById(R.id.tv_cook_description);
        cookDescriptionContainer.setText(cookDescriptionAndOtherVariable);
        return view;

    }
}