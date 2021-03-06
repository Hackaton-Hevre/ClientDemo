package com.hackaton.hevre.clientapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//import com.hackaton.hevre.clientapplication.Services.LocationService.LocationModelService;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;
import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;

/**
 * Created by Ron on 06/11/2016.
 */

public class BusinessListAdapter extends ArrayAdapter<Business> {

    /* constants */
    private static final int DEFAULT_LAYOUT_ID = -1;

    /* data members */
    private final Context mContext;
    private final ArrayList<Business> mBusinesses;
    private final double myLat;
    private final double myLng;
//    private LocationModelService locationService = new LocationModelService(this.getContext());

    /**
     * C-tor
     * @param context
     * @param businesses
     */
    public BusinessListAdapter(Context context, ArrayList<Business> businesses, double currentLat, double currentLng) {
        super(context, DEFAULT_LAYOUT_ID, businesses);
        this.mContext = context;
        this.mBusinesses = businesses;
        this.myLat = currentLat;
        this.myLng = currentLng;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.business_row_in_taks_list, parent, false);
        TextView primaryTextView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView secondaryTextView = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.goomapicon);

        // set view properties
        primaryTextView.setText(mBusinesses.get(position).getName());
        secondaryTextView.setText(mBusinesses.get(position).getAddress());
        imageView.setOnClickListener(new MapClickListener(position));
        return rowView;
        }


     private class MapClickListener implements View.OnClickListener {

        /* constant */
        private static final String GOOGLE_NAV_URI_PATT = "google.navigation:q=%s,%s";
        private static final String GOOGLE_ROUTE_URI_PATT = "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f";
        /* data members */
        private int mPosition;
        /* c-tors */
        public MapClickListener(int position) {
            this.mPosition = position;
        }

        public void onClick(View v) {
            double longitude = mBusinesses.get(mPosition).getLng();
            double latitude = mBusinesses.get(mPosition).getLat();
            String uri = String.format(GOOGLE_ROUTE_URI_PATT, myLat, myLng, latitude, longitude);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            mContext.startActivity(intent);

        }
    }

}
