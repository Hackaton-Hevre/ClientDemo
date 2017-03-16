package com.hackaton.hevre.clientapplication.Controller;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by אביחי on 12/03/2017.
 */

public class AnaleadFacebookCallback implements FacebookCallback<LoginResult> {

    private ProfileTracker mProfileTracker;
    private MainActivity mContext;
    private String mCurrentEmail;
    private Profile mCurrentProfile;

    public AnaleadFacebookCallback(MainActivity context)
    {
        mProfileTracker = new ProfileTracker() {
        @Override
        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
            mProfileTracker.stopTracking();
        }
    };
        mContext = context;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        mCurrentProfile = Profile.getCurrentProfile();
        if(mCurrentProfile == null) {
            mProfileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                    // profile2 is the new profile
                    Log.v("facebook - profile", profile2.getFirstName());
                    mProfileTracker.stopTracking();
                }
            };
        }

        // Facebook Email address
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            mCurrentEmail = object.getString("email");
                        } catch (JSONException e) {
                            mCurrentEmail = object.toString();
                        }
                        mContext.facebookLoginSuccess(mCurrentEmail);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public String getCurrentEmail()
    {
        return mCurrentEmail;
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    public Profile getCurrentProfile() {
        mCurrentProfile = Profile.getCurrentProfile();
        return mCurrentProfile;
    }
}
