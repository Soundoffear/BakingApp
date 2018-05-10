package com.example.soundoffear.bakingapp.models;

/**
 * Created by soundoffear on 24/03/2018.
 */

public class IngredientsModel {

    String inaQuantity;
    String ingMeasure;
    String ingIngredient;

    public IngredientsModel(String inaQuantity, String ingMeasure, String ingIngredient) {
        this.inaQuantity = inaQuantity;
        this.ingMeasure = ingMeasure;
        this.ingIngredient = ingIngredient;

    }

    public String getInaQuantity() {
        return inaQuantity;
    }

    public String getIngMeasure() {
        return ingMeasure;
    }

    public String getIngIngredient() {
        return ingIngredient;
    }
}
