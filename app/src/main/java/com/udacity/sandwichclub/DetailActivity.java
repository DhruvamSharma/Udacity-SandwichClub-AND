package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mOriginTV, mAlsoKnownAsTV, mIngredientsTV, mDescription, mMainNameTV;
    TextView mAlsoKnownAsLabel, mIngredientsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         * FindViewById Calls to different views in Detail Activity.
         */
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOriginTV = findViewById(R.id.origin_tv);
        mAlsoKnownAsTV = findViewById(R.id.also_known_tv);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mDescription = findViewById(R.id.description_tv);
        mMainNameTV = findViewById(R.id.main_name_tv);

        mAlsoKnownAsLabel = findViewById(R.id.also_known_label);
        mIngredientsLabel = findViewById(R.id.ingredients_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {

            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to populate data of different views
     */
    private void populateUI(Sandwich sandwich) {
        mMainNameTV.setText(sandwich.getMainName());
        mOriginTV.setText(sandwich.getPlaceOfOrigin());
        mDescription.setText(sandwich.getDescription());

        checkAndSetList(sandwich.getIngredients(), mIngredientsTV, mIngredientsLabel);
        checkAndSetList(sandwich.getAlsoKnownAs(), mAlsoKnownAsTV, mAlsoKnownAsLabel);
    }


    /**
     * Method to check list size.
     * If size is not 0, populate the Ingredient TextView
     * @param list
     */

    private void checkAndSetList(List<String> list, TextView dynamicTV, TextView dynamicLabel) {
        if(list.isEmpty()) {
            dynamicTV.setVisibility(View.GONE);
            dynamicLabel.setVisibility(View.GONE);
            return;
        }
        StringBuilder data = new StringBuilder();
        for( int i =0; i <list.size(); i++) {
            data.append(list.get(i));
            if ( i != list.size()-1)
            data.append(",");
        }

        dynamicTV.setText(data.toString());
    }
}
