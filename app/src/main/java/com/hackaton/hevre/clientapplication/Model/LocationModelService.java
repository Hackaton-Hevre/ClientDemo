package com.hackaton.hevre.clientapplication.Model;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.hackaton.hevre.clientapplication.Controller.HomeActivity;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by אביחי on 29/10/2016.
 */
public class LocationModelService implements ILocationModelService, LocationListener {

    public static final int MIN_TIME = 10000;
    public static final int MIN_DISTANCE = 1;
    private Context mContext;
    private final LocationManager mLocationManager;
    private Location mLastLocation = null;
    private Activity activity = null;

    public LocationModelService(Context context) {

        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        String provider = mLocationManager.getBestProvider(new Criteria(), true);

        try
        {
            mLocationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, this);
            mLastLocation = mLocationManager.getLastKnownLocation(provider);
        }
        catch (Exception e)
        {
            new LocationModelService(context);
        }
    }

    @Override
    public void setDelegate(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Location getCurrentLocation() {
        String provider = mLocationManager.getBestProvider(new Criteria(), true);
        Location locations = mLocationManager.getLastKnownLocation(provider);
        mLastLocation = locations;
        return mLastLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        ((HomeActivity) activity).locationChanged_callback(mLastLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
