package com.amyaglobal.find_cook.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.amyaglobal.find_cook.CookDescriptionFragment;
import com.amyaglobal.find_cook.CookSpecificationFragment;
import com.amyaglobal.find_cook.models.CookSpecificationFeaturesModel;

import java.util.List;

public class CookDescriptionViewPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;
    private String cookDescription;
    private String otherDetails;
    private List<CookSpecificationFeaturesModel> cookSpecificationFeaturesModelList;
    public CookDescriptionViewPagerAdapter(@NonNull FragmentManager fm, int tabCount, String cookDescription, String otherDetails, List<CookSpecificationFeaturesModel> cookSpecificationFeaturesModelList) {
        super(fm);
        this.tabCount = tabCount;
        this.cookDescription = cookDescription;
        this.otherDetails = otherDetails;
        this.cookSpecificationFeaturesModelList = cookSpecificationFeaturesModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
            CookDescriptionFragment cookDescriptionFragment = new CookDescriptionFragment();
            CookDescriptionFragment.cookDescriptionAndOtherVariable = cookDescription;
            return cookDescriptionFragment;
            case 1:
            CookSpecificationFragment cookSpecificationFragment = new CookSpecificationFragment();
            CookSpecificationFragment.cookSpecificationFeaturesModelList = cookSpecificationFeaturesModelList;
            return  cookSpecificationFragment;
            case 2:
            CookDescriptionFragment cookDescriptionFragment1 = new CookDescriptionFragment();
            CookDescriptionFragment.cookDescriptionAndOtherVariable = otherDetails;
            return cookDescriptionFragment1;
            default:
            return null;

        }
    }
//here return current tab count
    @Override
    public int getCount() {
        return tabCount;
    }
}
