package com.example.soundoffear.bakingapp;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by soundoffear on 07/04/2018.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable private volatile  ResourceCallback mCallback;

    private AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return atomicBoolean.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        atomicBoolean.set(isIdleNow);
        if(isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
