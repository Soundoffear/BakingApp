package com.example.soundoffear.bakingapp.models;

/**
 * Created by soundoffear on 24/03/2018.
 */

public class RecipeModel {

    private String recId;
    private String recName;
    private String recIngredients;
    private String recSteps;
    private String recServings;

    public RecipeModel(String recId, String recName) {
        this.recId = recId;
        this.recName = recName;
    }

    public RecipeModel(String recId, String recName, String recIngredients, String recSteps, String recServings) {
        this.recId = recId;
        this.recName = recName;
        this.recIngredients = recIngredients;
        this.recSteps = recSteps;
        this.recServings = recServings;
    }

    public String getRecId() {
        return recId;
    }

    public String getRecName() {
        return recName;
    }

    public String getRecIngredients() {
        return recIngredients;
    }

    public String getRecSteps() {
        return recSteps;
    }

    public String getRecServings() {
        return recServings;
    }
}
