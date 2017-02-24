package com.hackaton.hevre.clientapplication.Controller;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.IModelService;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.ModelService;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.TaskingStatus;
import com.hackaton.hevre.clientapplication.R;

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
    ActionBar mActionBar;
    TextView mTitleTextView;
    View mCustomView;

    /* Common behavior */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Action bar init */
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);

        /* Model service init */
        mModelService = ModelService.getInstance(this);
        mModelService.setDelegate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mModelService.setDelegate(this);
    }

    protected void setActionBarTitle(String title)
    {
        mTitleTextView.setText(title);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    /* HomeActivity callbacks */

    public void addtask_callback(TaskingStatus status, List<String> categories) {

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

    public void throwMessage_callback(String s) {
        Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
    }
}
