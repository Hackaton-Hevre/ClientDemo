package com.hackaton.hevre.clientapplication.Model.LocationService;

import android.location.Location;

import com.hackaton.hevre.clientapplication.Controller.AppCallbackActivity;

/**
 * Created by אביחי on 29/10/2016.
 */

public interface ILocationModelService {

    void setDelegate(AppCallbackActivity activity);

    Location getCurrentLocation();

}
