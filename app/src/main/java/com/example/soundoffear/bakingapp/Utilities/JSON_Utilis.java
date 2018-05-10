package com.example.soundoffear.bakingapp.Utilities;

import com.example.soundoffear.bakingapp.models.IngredientsModel;
import com.example.soundoffear.bakingapp.models.RecipeModel;
import com.example.soundoffear.bakingapp.models.StepsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSON_Utilis {

    public static List<RecipeModel> allRecipes(String s) {

        List<RecipeModel> recipeModelList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(s);

            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject recipe = jsonArray.getJSONObject(i);
                String recID = recipe.getString("id");
                String recName = recipe.getString("name");
                String recIngredients = recipe.getString("ingredients");
                String recSteps = recipe.getString("steps");
                String recServ = recipe.getString("servings");

                RecipeModel recipeModel = new RecipeModel(recID, recName, recIngredients, recSteps, recServ);

                recipeModelList.add(recipeModel);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipeModelList;
    }

    public static List<IngredientsModel> allIngredients(String s) {
        List<IngredientsModel> ingredientsModels = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(s);
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ingQuantity = jsonObject.getString("quantity");
                String ingMeasure = jsonObject.getString("measure");
                String ingIngredient = jsonObject.getString("ingredient");
                IngredientsModel ingredientsModel = new IngredientsModel(ingQuantity, ingMeasure, ingIngredient);
                ingredientsModels.add(ingredientsModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingredientsModels;
    }

    public static List<StepsModel> allSteps(String s) {
        List<StepsModel> stepsModelList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(s);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String sId = jsonObject.getString("id");
                String sShort = jsonObject.getString("shortDescription");
                String sDescription = jsonObject.getString("description");
                String sVideoURL = jsonObject.getString("videoURL");
                String sThumbnailURL = jsonObject.getString("thumbnailURL");
                StepsModel stepsModel = new StepsModel(sId, sShort, sDescription, sVideoURL, sThumbnailURL);
                stepsModelList.add(stepsModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stepsModelList;
    }

}
