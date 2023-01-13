package com.amyaglobal.find_cook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.amyaglobal.find_cook.adapters.CookAdapters;
import com.amyaglobal.find_cook.models.CookModels;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNewCook extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Dialog loadingDialog ;
    CircleImageView cookImage;
    EditText cookName;
    EditText cookPlace;
    EditText cookCharge;
    EditText cookDescription;
    EditText cookPrimaryPhone;
    EditText cookSecondaryPhone;
    EditText cookNonVegDish;
    EditText cookVegDish;
    Spinner isNegotiable;
    Spinner isCookingNonVeg;
    private String isCookingNonVegValue;
    private String isNegotiableValue;
    private Button addCook;
    private Uri cookPhotoUri ;
    private String cookImageDownloadUrl;

    static boolean isPhoneNumberVerified = false;

    FirebaseDatabase database;
    DatabaseReference reference;
    public static List<CookModels> cookModelsList;
    private CookAdapters cookAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cook);
        cookImage = findViewById(R.id.add_master_image);
        cookName = findViewById(R.id.add_cook_name);
        cookPlace = findViewById(R.id.add_cook_place);
        cookCharge = findViewById(R.id.add_cook_cost);
        cookDescription = findViewById(R.id.cook_description);
        cookPrimaryPhone = findViewById(R.id.add_cook_primary_number);
        cookSecondaryPhone = findViewById(R.id.add_cook_secondary_number);
        cookNonVegDish = findViewById(R.id.add_cook_veg_dish);
        cookVegDish = findViewById(R.id.add_cook_non_veg_dish);
        isNegotiable = findViewById(R.id.negotiable_spinner);
        isCookingNonVeg = findViewById(R.id.cookNonVeg_spinner);
        addCook = findViewById(R.id.add_new_cook_btn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");
        //loading Dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corner));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

    spinnerValue();
    setCookData();
    }
    private  void spinnerValue(){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> isCookingNonVegAdapter = ArrayAdapter.createFromResource(this,
                R.array.can_cook_nonVeg, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        isCookingNonVegAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        isCookingNonVeg.setSelection(0);
        isCookingNonVeg.setAdapter(isCookingNonVegAdapter);
        isCookingNonVeg.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        ArrayAdapter<CharSequence> isNegotiableAdapter = ArrayAdapter.createFromResource(this,
                R.array.price_negotiation, android.R.layout.simple_spinner_item);
        isNegotiableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isNegotiable.setSelection(0);
        isNegotiable.setAdapter(isCookingNonVegAdapter);
        isNegotiable.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        isCookingNonVegValue = isCookingNonVeg.getSelectedItem().toString();
        isNegotiableValue = isNegotiable.getSelectedItem().toString();

    }
//    spinner functions
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        switch (adapterView.getId()){
//            case R.id.negotiable_spinner:
//                isNegotiableValue =
//
//        }
    isNegotiableValue = adapterView.getItemAtPosition(i).toString();
//    Toast.makeText(adapterView.getContext(), isNegotiableValue, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private  void setCookData(){

        //image picker
        cookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 101);
            }
        });
//    check form field
        addCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cookName.getText().toString().isEmpty() || cookName.getText() == null){
                    cookName.setError("Required");
                    return;
                }
                if(cookPlace.getText().toString().isEmpty() || cookPlace.getText() == null){
                    cookPlace.setError("Required");
                    return;
                }
                if(cookCharge.getText().toString().isEmpty() || cookCharge.getText() == null){
                    cookCharge.setError("Required");
                    return;
                }
                if(cookDescription.getText().toString().isEmpty() || cookDescription.getText() == null){
                    cookDescription.setError("Required");
                    return;
                }
                if(cookPrimaryPhone.getText().toString().isEmpty() || cookPrimaryPhone.getText() == null){
                    cookPrimaryPhone.setError("Required");
                    return;
                }
                if (cookPhotoUri == null) {
                    Toast.makeText(AddNewCook.this, "Please Select Your Image", Toast.LENGTH_LONG).show();
                    return;
                }
//                primary phone number verification
                if(!isPhoneNumberVerified){
                    verifyPrimaryNumber();
                }else{
//    if all required field are correct then upload data
                    prepareUploadCookData();

                }
            }
        });
    }

    private void verifyPrimaryNumber() {
        Intent verifyPhone = new Intent(getApplicationContext() , PhoneNumberVerification.class);
        verifyPhone.putExtra("mobileNumber" , cookPrimaryPhone.getText().toString());
        startActivity(verifyPhone);
    }

    // over write activity result to set downloadable image url
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                cookPhotoUri = data.getData();
                cookImage.setImageURI(cookPhotoUri);
            }
        }
    }
    private void prepareUploadCookData() {
        loadingDialog.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference imageReference = storageReference.child("cookImages").child(cookPhotoUri.getLastPathSegment());

        UploadTask uploadTaskImage = imageReference.putFile(cookPhotoUri);
        Task<Uri> uriTask = uploadTaskImage.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw  task.getException();
                }
            // else Continue with the task to get the download URL
              return  imageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            cookImageDownloadUrl = task.getResult().toString();
                            uploadFinalCookData();
                        }else {
                            loadingDialog.dismiss();
                            Toast.makeText(AddNewCook.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {
                    // Handle failures
                    loadingDialog.dismiss();
                    Toast.makeText(AddNewCook.this, "Something Went Wrong", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private void uploadFinalCookData() {
        Map<String, Object> cookDataMap = new HashMap<>();
        Map<String, Object> cookNumberMap = new HashMap<>();
        Map<String, Object> cookingItemsMap = new HashMap<>();
        cookDataMap.put("cookName", cookName.getText().toString());
        cookDataMap.put("cookPlace", cookPlace.getText().toString());
        cookDataMap.put("costPerMonth", cookCharge.getText().toString());
        cookDataMap.put("cookDescription", cookDescription.getText().toString());
        cookDataMap.put("isCookingNonVeg", isCookingNonVegValue);
        cookDataMap.put("isNegotiable",  isNegotiableValue);

        cookNumberMap.put("primaryNumber", cookPrimaryPhone.getText().toString());
        cookNumberMap.put("secondaryNumber", cookSecondaryPhone.getText().toString());
        cookingItemsMap.put("Non-vegetarian", cookNonVegDish.getText().toString());
        cookingItemsMap.put("vegetarian", cookVegDish.getText().toString());

        cookDataMap.put("cookNumbers", cookNumberMap);
        cookDataMap.put("cookingItems", cookingItemsMap);

        cookDataMap.put("cookImageUrl", cookImageDownloadUrl);
        final String id = UUID.randomUUID().toString();
        cookDataMap.put("cookKeyID", id);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("cooks").child(id).setValue(cookDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddNewCook.this, "Data Added Successfully ", Toast.LENGTH_LONG).show();
                    cookModelsList.add(new CookModels(
                            cookName.getText().toString() ,
                            cookPlace.getText().toString() ,
                            cookCharge.getText().toString() ,
                            isNegotiableValue ,
                            isNegotiableValue,
                            cookDescription.getText().toString() ,
                            new ArrayList<String>() ,
                            new ArrayList<String>() ,
                            cookImageDownloadUrl ,
                            id));
//                    categoriesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AddNewCook.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                }
                loadingDialog.dismiss();
            }
        });
    }


}