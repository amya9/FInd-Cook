package com.amyaglobal.find_cook.models;

import java.util.List;

public class CookSpecificationFeaturesModel {
    public static final int COOK_FEATURES_TYPE_VIEW = 0;
    public static final int COOK_FEATURES_VALUE_VIEW = 1;
    public int type;
    private String cookFeaturesName;
    private String cookFeaturesValue;

    public CookSpecificationFeaturesModel(){
//        empty constructor
//        this.type = type;
    }

//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }

    //////////////////cook feature type
    private  String cookFeaturesType;

//    public CookSpecificationFeaturesModel(int type, String cookFeaturesType) {
//        this.type = type;
//        this.cookFeaturesType = cookFeaturesType;
//    }
//
//    public String getCookFeaturesType() {
//        return cookFeaturesType;
//    }
    //////////////////cook feature type

    //////////////////product feature value

    public CookSpecificationFeaturesModel( String cookFeaturesName, String cookFeaturesValue) {
        this.cookFeaturesName = cookFeaturesName;
        this.cookFeaturesValue = cookFeaturesValue;
    }

    public String getCookFeaturesName() {
        return cookFeaturesName;
    }

    public String getCookFeaturesValue() {
        return cookFeaturesValue;
    }

    public void setCookFeaturesName(String cookFeaturesName) {
        this.cookFeaturesName = cookFeaturesName;
    }

    public void setCookFeaturesValue(String cookFeaturesValue) {
        this.cookFeaturesValue = cookFeaturesValue;
    }

    //////////////////product feature value

}
