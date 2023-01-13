package com.amyaglobal.find_cook.models;

import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class CookModels {
    private String cookName ;
    private String cookPlace ;
    private String costPerMonth ;
    private String isNegotiable ;
    private String isCookingNonVeg ;
    private String cookDescription ;
    private List<String> cookNumbers;
    private List<String> cookingItems;
    private String imageUrl;
    private String keyID;

    public CookModels(){
//        empty constructor
    }

    public CookModels(String cookName , String cookPlace , String costPerMonth , String isNegotiable , String isCookingNonVeg , String cookDescription , List<String> cookNumbers , List<String> cookingItems , String imageUrl , String keyID){

    this.cookName = cookName;
    this.cookPlace = cookPlace;
    this.costPerMonth = costPerMonth;
    this.isNegotiable = isNegotiable;
    this.isCookingNonVeg = isCookingNonVeg;
    this.cookDescription = cookDescription;
    this.cookNumbers = cookNumbers;
    this.cookingItems = cookingItems;
    this.imageUrl = imageUrl;
    this.keyID = keyID;

    }


    public String getCookName() {
        return cookName;
    }

    public String getCookPlace() {
        return cookPlace;
    }

    public String getCostPerMonth() {
        return costPerMonth;
    }

    public String getIsNegotiable() {
        return isNegotiable;
    }

    public String getIsCookingNonVeg() {
        return isCookingNonVeg;
    }

    public String getCookDescription() {
        return cookDescription;
    }

    public List<String> getCookNumbers() {
        return cookNumbers;
    }

    public List<String> getCookingItems() {
        return cookingItems;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getKeyID() {
        return keyID;
    }

//    setter

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public void setCookPlace(String cookPlace) {
        this.cookPlace = cookPlace;
    }

    public void setCostPerMonth(String costPerMonth) {
        this.costPerMonth = costPerMonth;
    }

    public void setIsNegotiable(String isNegotiable) {
        this.isNegotiable = isNegotiable;
    }

    public void setIsCookingNonVeg(String isCookingNonVeg) {
        this.isCookingNonVeg = isCookingNonVeg;
    }

    public void setCookDescription(String cookDescription) {
        this.cookDescription = cookDescription;
    }

    public void setCookNumbers(List<String> cookNumbers) {
        this.cookNumbers = cookNumbers;
    }

    public void setCookingItems(List<String> cookingItems) {
        this.cookingItems = cookingItems;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }
}
