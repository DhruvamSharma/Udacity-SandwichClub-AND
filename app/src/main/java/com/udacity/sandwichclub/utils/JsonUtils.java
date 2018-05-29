package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        /**
         * Step 1: Creating a sandwich object to fill data with and send back to the calling method.
         * Step 2: Parsing the json data and setting it in the newly created sandwich object.
         * Step 3: Sending the sanswich object back.
         */

        /**
         * Created a helper method jsonToStringArray to convert jsonArray to String Array.
         */

        Sandwich sandwich = new Sandwich();

        // parsing json
        try {
            JSONObject jsonObject = new JSONObject(json);

            sandwich.setMainName(jsonObject.getJSONObject("name").getString("mainName"));
            sandwich.setAlsoKnownAs( jsonToStringArray(jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs")) );
            sandwich.setIngredients( jsonToStringArray(jsonObject.getJSONArray("ingredients")) );
            sandwich.setDescription( jsonObject.getString("description"));
            sandwich.setImage( jsonObject.getString("image"));
            sandwich.setPlaceOfOrigin( jsonObject.getString("placeOfOrigin"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }





    // HELPER METHODS

    private static List<String> jsonToStringArray(JSONArray array) {
        List<String> list = new ArrayList<>();

        for( int i =0; i < array.length() ; i++ ) {
            try {
                list.add(array.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
