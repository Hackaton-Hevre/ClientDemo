package com.hackaton.hevre.clientapplication.Controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackaton.hevre.clientapplication.Model.Business;
import com.hackaton.hevre.clientapplication.Model.BusinessListAdapter;
import com.hackaton.hevre.clientapplication.R;

import com.hackaton.hevre.clientapplication.Communication.GoogleApiWrapper;

import java.util.ArrayList;

/**
 * Created by Ron on 30/10/2016.
 */

public class TaskActivity extends AppCallbackActivity implements AdapterViewCompat.OnItemClickListener {

    /* constants */
    private static final int RADIUS = 500;

    /* data members */
    private GoogleApiWrapper googleApi = GoogleApiWrapper.getInstance();
    private ListView mListView;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        String taskName = (String) getIntent().getSerializableExtra("taskName");
        double latitude = (double) getIntent().getSerializableExtra("lat");
        double longitude = (double) getIntent().getSerializableExtra("lng");

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.task_toolbar);
        mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(taskName);
        (new BusinessesGetter(this, latitude, longitude)).execute();
    }

    @Override
    public void onItemClick(AdapterViewCompat<?> parent, View view, int position, long id) {

    }

    public class BusinessesGetter extends AsyncTask<String, Void, String> {

        /* data members */
        private double lat;
        private double lng;
        Context context;

        public BusinessesGetter(Context context, double latitude, double longitude) {
            this.lat = latitude;
            this.lng = longitude;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            String googleMapReq = googleApi.buildGmapsRequest(lng, lat, RADIUS);
            String googleMapsResp = googleApi.get(googleMapReq);
            return googleMapsResp;
        }

        @Override
        protected void onPostExecute(String resp) {
            ArrayList<Business> businesses = googleApi.getBusinesses(resp);
            ArrayAdapter<Business> adapter = new BusinessListAdapter(this.context, businesses);
            mListView = (ListView) findViewById(R.id.listView);
            mListView.setAdapter(adapter);
        }
    }
}
