package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        // If the JSON string is empty or null, then return early.
        if(TextUtils.isEmpty(json)){
            return null;
        }

        final String swName = "name";
        final String swMainName = "mainName";
        final String swAlsoKnownAs = "alsoKnownAs";
        final String swPlaceOfOrigin = "placeOfOrigin";
        final String swDescription = "description";
        final String swImage = "image";
        final String swIngredients = "ingredients";

        String mainName = "";
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = new ArrayList<>();

        try {

            // Create a base JSONObject
            JSONObject sandwichJSON = new JSONObject(json);

            // Check if the name key exists
            if(sandwichJSON.has(swName)) {

                // Get the name object
                JSONObject sandwichName = sandwichJSON.getJSONObject(swName);

                // Get the main name
                if(sandwichName.has(swMainName)) {
                    mainName = sandwichName.getString(swMainName);
                }

                // Get the also known as names
                if(sandwichName.has(swAlsoKnownAs)) {
                    JSONArray sandwichAlsoKnownAs = sandwichName.getJSONArray(swAlsoKnownAs);
                    if(sandwichAlsoKnownAs.length() >= 1) {
                        for (int i = 0; i < sandwichAlsoKnownAs.length(); i++) {
                            String currentName = sandwichAlsoKnownAs.getString(i);
                            alsoKnownAs.add(currentName);
                        }
                    } else {
                        alsoKnownAs.add("No Alternative Names");
                    }
                }
            }

            // Get the place of origin
            if(sandwichJSON.has(swPlaceOfOrigin)) {
                placeOfOrigin = sandwichJSON.getString(swPlaceOfOrigin);
            }

            // Get the description
            if(sandwichJSON.has(swDescription)) {
                description = sandwichJSON.getString(swDescription);
            }

            // Get the image
            if(sandwichJSON.has(swImage)) {
                image = sandwichJSON.getString(swImage);
            }

            // Get the ingredients
            if(sandwichJSON.has(swIngredients)){
                JSONArray sandwichIngredients = sandwichJSON.getJSONArray(swIngredients);
                if(sandwichIngredients.length() >= 1) {
                    for (int i = 0; i < sandwichIngredients.length(); i++) {
                        String currentIngredient = sandwichIngredients.getString(i);
                        ingredients.add(currentIngredient);
                    }
                } else {
                    ingredients.add("No Ingredients Listed");
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem parsing JSON ", e);
        }

        Log.v(LOG_TAG, "Sandwich: " + mainName + " " + alsoKnownAs + " " + placeOfOrigin + " " + description + " " + image + " " + ingredients);
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
