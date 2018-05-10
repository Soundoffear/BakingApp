package com.example.soundoffear.bakingapp.models;

/**
 * Created by soundoffear on 25/03/2018.
 */

public class StepsModel {

    private String stepId;
    private String stepShortDescription;
    private String stepDescription;
    private String stepVideoURL;
    private String stepThumbnailURL;

    public StepsModel(String stepId, String stepShortDescription, String stepDescription, String stepVideoURL, String stepThumbnailURL) {
        this.stepId = stepId;
        this.stepShortDescription = stepShortDescription;
        this.stepDescription = stepDescription;
        this.stepVideoURL = stepVideoURL;
        this.stepThumbnailURL = stepThumbnailURL;
    }

    public String getStepId() {
        return stepId;
    }

    public String getStepShortDescription() {
        return stepShortDescription;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getStepVideoURL() {
        return stepVideoURL;
    }

    public String getStepThumbnailURL() {
        return stepThumbnailURL;
    }
}
