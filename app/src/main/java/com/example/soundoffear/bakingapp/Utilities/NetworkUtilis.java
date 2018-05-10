package com.example.soundoffear.bakingapp.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by soundoffear on 24/03/2018.
 */

public class NetworkUtilis {

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildURL() {
        URL newURL = null;
        try {
            newURL = new URL(RECIPE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newURL;
    }

    public static String downloadJSON_RecipeList(URL recipeURL) throws IOException {

        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        String result = null;

        try {
            httpURLConnection = (HttpURLConnection) recipeURL.openConnection();

            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(3000);

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();

            if(responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code " + responseCode);
            }

            inputStream = httpURLConnection.getInputStream();
            if(inputStream != null) {
                result = readStream(inputStream);
            }

        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return result;

    }

    private static String readStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder finalBuild = new StringBuilder();
        String sFinal;
        while ((sFinal = bufferedReader.readLine()) != null) {
            finalBuild.append(sFinal).append('\n');
        }
        return finalBuild.toString();
    }

}
