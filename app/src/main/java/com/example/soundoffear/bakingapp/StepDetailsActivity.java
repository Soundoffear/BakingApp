package com.example.soundoffear.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.soundoffear.bakingapp.Utilities.ExoplayerHandlerUtility;
import com.example.soundoffear.bakingapp.Utilities.JSON_Utilis;
import com.example.soundoffear.bakingapp.models.StepsModel;
import com.example.soundoffear.bakingapp.recipeFragments.DetailsFragmentStep;
import com.example.soundoffear.bakingapp.recipeFragments.RecipeStepsFragment;

import java.util.List;

public class StepDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_ID = "step_id";
    public static final String BUNDLE_TITLE = "step_title";
    public static final String BUNDLE_DESC = "step_description";
    public static final String BUNDLE_VIDEO = "step_video";
    public static final String BUNDLE_POS = "step_position";
    public static final String BUNDLE_THUMB = "step_thumbs";

    int nextPosition = 0;
    private List<StepsModel> stepsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Button nextButton = findViewById(R.id.steps_next_button2);
        Button prevButton = findViewById(R.id.steps_previous_back_button);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        Intent receivedIntent = getIntent();
        Bundle receivedData = receivedIntent.getExtras();
        assert receivedData != null;
        String stringData = receivedData.getString(RecipeStepsFragment.RECIPE_BUNDLE);
        nextPosition = receivedData.getInt(RecipeStepsFragment.RECIPE_POSITION);
        stepsModelList = JSON_Utilis.allSteps(stringData);

        DetailsFragmentStep detailsFragmentStep = new DetailsFragmentStep();

        Bundle stepBundle = new Bundle();
        stepBundle.putString(BUNDLE_ID, stepsModelList.get(nextPosition).getStepId());
        stepBundle.putString(BUNDLE_TITLE, stepsModelList.get(nextPosition).getStepShortDescription());
        stepBundle.putString(BUNDLE_DESC, stepsModelList.get(nextPosition).getStepDescription());
        stepBundle.putString(BUNDLE_VIDEO, stepsModelList.get(nextPosition).getStepVideoURL());
        stepBundle.putString(BUNDLE_THUMB, stepsModelList.get(nextPosition).getStepThumbnailURL());
        stepBundle.putInt(BUNDLE_POS, nextPosition);

        detailsFragmentStep.setArguments(stepBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.step_detail_fragment, detailsFragmentStep);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.steps_next_button2: {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailsFragmentStep detailsFragmentStep = new DetailsFragmentStep();
                if(nextPosition < stepsModelList.size()-1) {
                    nextPosition++;
                }
                Bundle nextFragmentBundle = new Bundle();
                nextFragmentBundle.putString(BUNDLE_TITLE, stepsModelList.get(nextPosition).getStepShortDescription());
                nextFragmentBundle.putString(BUNDLE_DESC, stepsModelList.get(nextPosition).getStepDescription());
                nextFragmentBundle.putString(BUNDLE_VIDEO, stepsModelList.get(nextPosition).getStepVideoURL());
                nextFragmentBundle.putString(BUNDLE_THUMB, stepsModelList.get(nextPosition).getStepThumbnailURL());
                detailsFragmentStep.setArguments(nextFragmentBundle);
                fragmentTransaction.replace(R.id.step_detail_fragment, detailsFragmentStep);;
                fragmentTransaction.commit();
            }
            break;
            case R.id.steps_previous_back_button: {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailsFragmentStep detailsFragmentStep = new DetailsFragmentStep();
                if (nextPosition > 0) {
                    nextPosition--;
                }

                Bundle nextFragmentBundle = new Bundle();
                nextFragmentBundle.putString(BUNDLE_TITLE, stepsModelList.get(nextPosition).getStepShortDescription());
                nextFragmentBundle.putString(BUNDLE_DESC, stepsModelList.get(nextPosition).getStepDescription());
                nextFragmentBundle.putString(BUNDLE_VIDEO, stepsModelList.get(nextPosition).getStepVideoURL());
                nextFragmentBundle.putString(BUNDLE_THUMB, stepsModelList.get(nextPosition).getStepThumbnailURL());
                detailsFragmentStep.setArguments(nextFragmentBundle);
                fragmentTransaction.replace(R.id.step_detail_fragment, detailsFragmentStep);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        ExoplayerHandlerUtility.getInstance().releasePlayer();
        super.onBackPressed();
    }
}
