package com.amyaglobal.find_cook.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amyaglobal.find_cook.CookDetails;
import com.amyaglobal.find_cook.R;
import com.amyaglobal.find_cook.models.CookModels;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CookAdapters extends RecyclerView.Adapter<CookAdapters.ViewHolder> {
    private List<CookModels> cookModelsList;

    public CookAdapters(List<CookModels> cookModelsList) {
        this.cookModelsList = cookModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cook_details , parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(cookModelsList.get(position).getCookName() ,
                cookModelsList.get(position).getCookPlace(),
                cookModelsList.get(position).getCostPerMonth(),
                cookModelsList.get(position).getIsNegotiable(),
                cookModelsList.get(position).getIsCookingNonVeg(),
                cookModelsList.get(position).getCookDescription(),
                cookModelsList.get(position).getCookNumbers(),
                cookModelsList.get(position).getCookingItems(),
                cookModelsList.get(position).getImageUrl() ,
                cookModelsList.get(position).getKeyID() , position);

    }

    @Override
    public int getItemCount() {
        return cookModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView cookImage;
        private TextView cookName;
        private TextView cookPlace;
        private TextView costPerMonth;
        private TextView isNegotiable;
        private TextView isCookingNonVeg;
        private TextView cookDescription;
        private TextView cookNumbers;
        private TextView cookingItems;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cookImage = itemView.findViewById(R.id.cook_image);
            cookName = itemView.findViewById(R.id.cook_name);
            cookPlace = itemView.findViewById(R.id.cook_place);
            costPerMonth = itemView.findViewById(R.id.cook_charge);
        }
        private void setData( final String cookName , final String cookPlace , final String costPerMonth , final String isNegotiable , final String isCookingNonVeg ,
                              final String cookDescription , List<String>cookNumbers , List<String>cookingItems , final String cookImage  ,
                              final String keyID , final int  position){
            Glide.with(itemView.getContext()).load(cookImage).into(this.cookImage);
            this.cookName.setText(cookName);
            this.cookPlace.setText(cookPlace );
            this.costPerMonth.setText("₹ " +costPerMonth+" /.");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent cookDetailsIntent = new Intent(itemView.getContext() , CookDetails.class);
                    Bundle args = new Bundle();
                    cookDetailsIntent.putExtra("cookName" , cookName);
                    cookDetailsIntent.putExtra("cookPlace" , cookPlace);
                    cookDetailsIntent.putExtra("costPerMonth" , costPerMonth);
                    cookDetailsIntent.putExtra("isNegotiable" , isNegotiable);
                    cookDetailsIntent.putExtra("isCookingNonVeg" , isCookingNonVeg);
                    cookDetailsIntent.putExtra("cookDescription" , cookDescription);
//                    cookDetailsIntent.putExtra("cookNumbers" , (ArrayList) cookNumbers);
                    args.putSerializable("cookNumbers" , (Serializable) cookNumbers);
                    args.putSerializable("cookingItems" , (Serializable) cookingItems);
                    cookDetailsIntent.putExtra("cookingItems" , (ArrayList) cookingItems);
                    cookDetailsIntent.putExtra("cookImage" , cookImage);
                    cookDetailsIntent.putExtra("cookKeyID" , keyID);
                    cookDetailsIntent.putExtra("cookPosition" , position);
                    cookDetailsIntent.putExtra("BUNDLE" , args);

                    itemView.getContext().startActivity(cookDetailsIntent);
                }
            });

        }
    }
}
