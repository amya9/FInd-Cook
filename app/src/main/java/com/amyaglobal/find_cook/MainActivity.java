package com.amyaglobal.find_cook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.amyaglobal.find_cook.adapters.CookAdapters;
import com.amyaglobal.find_cook.models.CookModels;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private Dialog loadingDialog ;
    private RecyclerView cookRecyclerView ;
    private Button addNewCook;
    FirebaseDatabase database;
    DatabaseReference reference;
    public static List<CookModels> cookModelsList;
    private CookAdapters cookAdapters;
    private SearchView searchViewCook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Educator's");
        addNewCook = findViewById(R.id.add_cook_btn);
        searchViewCook = findViewById(R.id.searchViewCook);
        addNewCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCook = new Intent(view.getContext() , AddNewCook.class);
                view.getContext().startActivity(addCook);
            }
        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        //loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corner));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        cookRecyclerView = findViewById(R.id.cook_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        cookRecyclerView.setLayoutManager(linearLayoutManager);
        loadingDialog.show();
        cookModelsList = new ArrayList<>();

        cookAdapters = new CookAdapters(cookModelsList);
        cookRecyclerView.setAdapter(cookAdapters);
        loadingDialog.show();

        reference.child("cooks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    List<String> cookNumber = new ArrayList<>();
                    List<String> cookingItems = new ArrayList<>();

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("cookNumbers").getChildren()){
                        cookNumber.add( dataSnapshot1.getValue().toString());
                    }

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("cookingItems").getChildren()){
                        cookingItems.add((String) dataSnapshot1.getValue());
                    }
                    cookModelsList.add(new CookModels(
                    dataSnapshot.child("cookName").getValue().toString() ,
                    dataSnapshot.child("cookPlace").getValue().toString() ,
                    dataSnapshot.child("costPerMonth").getValue().toString() ,
                    dataSnapshot.child("isNegotiable").getValue().toString() ,
                    dataSnapshot.child("isCookingNonVeg").getValue().toString() ,
                    dataSnapshot.child("cookDescription").getValue().toString() ,
                    cookNumber ,
                    cookingItems ,
                    dataSnapshot.child("cookImageUrl").getValue().toString() ,
                    dataSnapshot.getKey()
                    ));
                }
                cookAdapters.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this , error.getMessage() , Toast.LENGTH_LONG).show();
                loadingDialog.dismiss();
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(searchViewCook != null){
            searchViewCook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    searchCook(s);
                    return true;
                }
            });
        }
    }

    private void searchCook(String str) {
     List<CookModels> cookModelsListSearchView = new ArrayList<>();
     for(CookModels object : cookModelsList ){
         if(object.getCookPlace().toLowerCase().contains(str.toLowerCase())){
             cookModelsListSearchView.add(object);
             Log.e("cook place" , object.getCookPlace());
         }
     }
     CookAdapters cookAdapters1 =  new CookAdapters(cookModelsListSearchView);
     cookRecyclerView.setAdapter(cookAdapters1);
     cookAdapters1.notifyDataSetChanged();

    }
}
