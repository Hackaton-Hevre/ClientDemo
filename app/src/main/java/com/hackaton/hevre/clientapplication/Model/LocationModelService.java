package com.hackaton.hevre.clientapplication.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.hackaton.hevre.clientapplication.Controller.AppCallbackActivity;
import com.hackaton.hevre.clientapplication.R;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.GpsStatus.GPS_EVENT_STARTED;
import static android.location.GpsStatus.GPS_EVENT_STOPPED;

/**
 * Created by אביחי on 29/10/2016.
 */
public class LocationModelService extends BroadcastReceiver implements ILocationModelService, LocationListener {

    public static final int MIN_TIME = 10000;
    public static final int MIN_DISTANCE = 0;
    private AppCallbackActivity mContext;
    private final LocationManager mLocationManager;
    private Location mLastLocation = null;
    private AppCallbackActivity activity = null;
    private boolean mIsGpsOn;

    public LocationModelService(AppCallbackActivity context) {

        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        mLocationManager.addGpsStatusListener(new android.location.GpsStatus.Listener()
        {
            public void onGpsStatusChanged(int event)
            {
                onGpsChanges(event);
            }
        });
        try
        {
            openGpsService();
        }
        catch (Exception e)
        {
        }
    }

    private void openGpsService() {
        String provider = mLocationManager.getBestProvider(new Criteria(), true);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            mLocationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, this);
            mLastLocation = mLocationManager.getLastKnownLocation(provider);
            mIsGpsOn = true;
        }
        else
        {
            mContext.throwMessage_callback(mContext.getString(R.string.locationMangerService_openGpsMessage));
            mIsGpsOn = false;
        }
    }

    @Override
    public void setDelegate(AppCallbackActivity activity) {
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
        activity.locationChanged_callback(mLastLocation);
    }

    public void onGpsChanges(int event)
    {
        switch(event)
        {
            case GPS_EVENT_STARTED:
                openGpsService();
                break;
            case GPS_EVENT_STOPPED:
                break;
        }
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

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
