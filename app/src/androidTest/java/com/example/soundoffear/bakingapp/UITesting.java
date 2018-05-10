package com.example.soundoffear.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by soundoffear on 07/04/2018.
 */

@RunWith(AndroidJUnit4.class)

public class UITesting {

    @Rule
    public IntentsTestRule<RecipeListActivity> mAndroidTest = new IntentsTestRule<>(RecipeListActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registeringIdlingResource() {
        idlingResource = mAndroidTest.getActivity().getmIdlingResource();

        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void testRecyclerViewClick() {
        onView(withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @After
    public void unregisterIdlingResource() {
        if(idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

}
