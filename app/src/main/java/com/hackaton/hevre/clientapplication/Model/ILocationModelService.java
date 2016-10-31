package com.hackaton.hevre.clientapplication.Model;

import android.app.Activity;
import android.location.Location;

/**
 * Created by אביחי on 29/10/2016.
 */

public interface ILocationModelService {

    void setDelegate(Activity activity);

    Location getCurrentLocation();

}
