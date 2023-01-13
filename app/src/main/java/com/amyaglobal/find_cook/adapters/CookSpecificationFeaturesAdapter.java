package com.amyaglobal.find_cook.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amyaglobal.find_cook.R;
import com.amyaglobal.find_cook.models.CookSpecificationFeaturesModel;

import java.util.List;

public class CookSpecificationFeaturesAdapter extends RecyclerView.Adapter<CookSpecificationFeaturesAdapter.ViewHolder> {
    private List<CookSpecificationFeaturesModel> cookSpecificationFeaturesModels;

    public CookSpecificationFeaturesAdapter(List<CookSpecificationFeaturesModel> cookSpecificationFeaturesModels) {
        this.cookSpecificationFeaturesModels = cookSpecificationFeaturesModels;
    }

    @NonNull

    @Override
//    public int getItemViewType(int position) {
//        switch (cookSpecificationFeaturesModels.get(position).getType()) {
//            case 0:
//                return cookSpecificationFeaturesModels.COOK_FEATURES_TYPE_VIEW;
//            case 1:
//                return cookSpecificationFeaturesModels.COOK_FEATURES_VALUE_VIEW;
//            default:
//                return -1;
//        }
//    }

    public CookSpecificationFeaturesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cook_specification_features, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CookSpecificationFeaturesAdapter.ViewHolder holder, int position) {
                String featureName = cookSpecificationFeaturesModels.get(position).getCookFeaturesName();
                String featureValue = cookSpecificationFeaturesModels.get(position).getCookFeaturesValue();
                holder.setFeatureName(featureName);
                holder.setFeatureValue(featureValue);
                return;
    }

    @Override
    public int getItemCount() {
        return cookSpecificationFeaturesModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView featureName, featureValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        private void setFeatureName(String name) {
            featureName = itemView.findViewById(R.id.cook_features_name);
            featureName.setText(name);
        }

        private void setFeatureValue(String value) {
            featureValue = itemView.findViewById(R.id.cook_features_value);
            featureValue.setText(value);
        }
    }

    private int convertDpIntoPixels(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
