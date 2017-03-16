package com.hackaton.hevre.clientapplication.Controller;

import com.facebook.Profile;
import com.facebook.ProfileTracker;

/**
 * Created by אביחי on 12/03/2017.
 */

public class AnaleadProfileTracker extends ProfileTracker {

    @Override
    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
        stopTracking();
    }
}
