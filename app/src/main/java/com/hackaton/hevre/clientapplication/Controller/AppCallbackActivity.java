package com.hackaton.hevre.clientapplication.Controller;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hackaton.hevre.clientapplication.Model.Business;
import com.hackaton.hevre.clientapplication.Model.IModelService;
import com.hackaton.hevre.clientapplication.Model.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.ModelService;
import com.hackaton.hevre.clientapplication.Model.TaskingStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by אביחי on 07/11/2016.
 *
 * This class is a general type for all our app activity and defines all the callbacks in the app.
 *
 * This removes the need of casting the delegate activity in the services classes.
 *
 * This removes the need of handeling setDelegate for the modelService.
 *
 * Every callback function will be defined in this class and implemented in suitable activity.
 *
 */

public abstract class AppCallbackActivity extends AppCompatActivity {

    /* Common members */
    IModelService mModelService;

    /* Common behavior */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModelService = ModelService.getInstance(this);
        mModelService.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mModelService.setDelegate(this);
    }

    /* HomeActivity callbacks */

    public void addtask_callback(TaskingStatus status) {

    }

    public void UserProducts_callback(List<String> userProducts) {

    }

    public void locationChanged_callback(Location location) {

    }

    public void pushNotification_callback(ArrayList<Business> relevantBusinesses) {

    }

    /* MainActivity callbacks */

    public void loginCallback(LoginStatus status) {

    }

    /* RegisterActivity callbacks */

    public void register_callback(LoginStatus status) {

    }
}
