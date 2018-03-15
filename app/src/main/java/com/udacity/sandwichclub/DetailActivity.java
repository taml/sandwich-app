package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private TextView alsoKnownAsTv;
    private TextView originTv;
    private TextView ingredientsTv;
    private TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

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
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sw) {

        // Set the also known as detail
        List<String> alsoKnownAs = sw.getAlsoKnownAs();
        StringBuilder knownAs = new StringBuilder("");
        for(int i = 0; i < alsoKnownAs.size(); i++){
            if(i != (alsoKnownAs.size() -1)){
                knownAs.append(alsoKnownAs.get(i));
                knownAs.append(", ");
            } else {
                knownAs.append(" ");
                knownAs.append(alsoKnownAs.get(i));
            }
        }
        alsoKnownAsTv.setText(knownAs);

        // Set the origin detail
        if(!sw.getPlaceOfOrigin().equals("")) {
            originTv.setText(sw.getPlaceOfOrigin());
        } else {
            originTv.setText(getString(R.string.origin_unknown));
        }

        // Set the description detail
        if(!sw.getDescription().equals("")) {
            descriptionTv.setText(sw.getDescription());
        } else {
            descriptionTv.setText(getString(R.string.description_unavailable));
        }

        // Set the ingredients detail
        List<String> ingredients = sw.getIngredients();
        StringBuilder ingreds = new StringBuilder("");
        for(int i = 0; i < ingredients.size(); i++){
            if(i != (ingredients.size() -1)){
                ingreds.append(ingredients.get(i));
                ingreds.append(", ");
            } else {
                ingreds.append(" ");
                ingreds.append(ingredients.get(i));
            }
        }
        ingredientsTv.setText(ingreds);
    }
}
