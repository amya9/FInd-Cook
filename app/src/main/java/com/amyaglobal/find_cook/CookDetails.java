package com.amyaglobal.find_cook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amyaglobal.find_cook.adapters.CookDescriptionViewPagerAdapter;
import com.amyaglobal.find_cook.models.CookSpecificationFeaturesModel;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CookDetails extends AppCompatActivity {
    private ImageView cookImageViewPager;
    private TextView cookNameView;
    private  TextView costPerMonth;
    private FrameLayout cookSpecification;
    private FrameLayout cookDescription;
    private ConstraintLayout otherCookDetails;
    private ConstraintLayout cookDescriptionContainer;
    private ViewPager cookDescriptionViewPager;
    private TabLayout cookDescriptionTabLayout;
    private Dialog loadingDialog ;
    public static TextView othersDetails;


    private String cookImageViewIntent;
    private String cookNameViewIntent;
    private String  costPerMonthViewIntent ;
    private String  cookPlaceViewIntent ;
    private String  isNegotiableViewIntent ;
    private String  isCookingNonVegViewIntent ;
    private String  cookDescriptionViewIntent ;
    private ArrayList<String> cookingItemsViewIntent ;
    private ArrayList<String> cookNumbersViewIntent;

    private ArrayList cookSpecificationFeaturesModelList;

//    buttons
    private LinearLayout bookMarkCook;
    private Button callNowBtn;

    FirebaseDatabase database;
    DatabaseReference reference;

    public CookDetails() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_details);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        cookNameViewIntent = getIntent().getStringExtra("cookName");
        cookImageViewIntent = getIntent().getStringExtra("cookImage");
        costPerMonthViewIntent = getIntent().getStringExtra("costPerMonth");
        cookPlaceViewIntent = getIntent().getStringExtra("cookPlace");
        isNegotiableViewIntent = getIntent().getStringExtra("isNegotiable");
        isCookingNonVegViewIntent = getIntent().getStringExtra("isCookingNonVeg");
        cookDescriptionViewIntent = getIntent().getStringExtra("cookDescription");

//        get arraylist value from intent
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        cookNumbersViewIntent = args.getStringArrayList("cookNumbers");
        cookingItemsViewIntent = args.getStringArrayList("cookingItems");
        Log.e("number  final" , cookNumbersViewIntent.get(0));

        //loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corner));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);
        //loading category Dialog

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(cookNameViewIntent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        setImageLayout();
        setDescriptionLayout();
        callNowBtn = findViewById(R.id.call_now_btn);
        callNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CallNow();
            }
        });

        bookMarkCook = findViewById(R.id.add_to_bookmark_linearlayout);
        bookMarkCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookMarkCookFunction();
            }
        });
    }



    private void setImageLayout() {
        cookImageViewPager = findViewById(R.id.cook_image_viewPager);
        cookNameView = findViewById(R.id.cook_name_mini_view);
        costPerMonth = findViewById(R.id.cook_discounted_price_tv);

        Glide.with(this).load(cookImageViewIntent).into(cookImageViewPager);
        cookNameView.setText(cookNameViewIntent);
//        this.cookPlace.setText(cookPlace );
        costPerMonth.setText("₹ " +costPerMonthViewIntent+" /.");
    }

//        set details in description layout
    private void setDescriptionLayout() {
        cookSpecification = findViewById(R.id.cook_specification_fragment);
        cookDescription = findViewById(R.id.cook_description_fragment);
        otherCookDetails = findViewById(R.id.other_cook_details_container);
        cookDescriptionContainer = findViewById(R.id.product_description_container);
        othersDetails = findViewById(R.id.other_cook_details);
        cookDescriptionViewPager = findViewById(R.id.cook_description_viewPager);
        cookDescriptionTabLayout = findViewById(R.id.cook_description_tabLayout);


        cookSpecificationFeaturesModelList = new ArrayList();
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Cook Name" , cookNameViewIntent));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Cook Working Place" , cookPlaceViewIntent));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Charge Per Month(₹)" , costPerMonthViewIntent));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Charges are negotiable?" , isNegotiableViewIntent));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Can cook Non-vegetarian items?" , isCookingNonVegViewIntent));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Cook Primary Number" , cookNumbersViewIntent.get(0)));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Cook Secondary Number" , cookNumbersViewIntent.get(1)));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Non-Vegetarian items can cook?" , cookingItemsViewIntent.get(0)));
        cookSpecificationFeaturesModelList.add(new CookSpecificationFeaturesModel("Vegetarian items can cook?" , cookingItemsViewIntent.get(1)));

        cookDescriptionViewPager.setAdapter(new CookDescriptionViewPagerAdapter(getSupportFragmentManager() , cookDescriptionTabLayout.getTabCount() , cookDescriptionViewIntent , cookDescriptionViewIntent , cookSpecificationFeaturesModelList));
        cookDescriptionViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(cookDescriptionTabLayout));
        cookDescriptionTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cookDescriptionViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        cookDescriptionTV.setText(cookDescriptionViewIntent);


    }

//call feature
    private void CallNow() {
        String cookPrimaryNumber = cookNumbersViewIntent.get(0);

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.fromParts("tel", cookPrimaryNumber , null));
        startActivity(callIntent);
    }

//    bookmark cook features
    private void bookMarkCookFunction() {
        Toast.makeText(getApplicationContext() , "Coming Soon" , Toast.LENGTH_SHORT).show();
    }

}