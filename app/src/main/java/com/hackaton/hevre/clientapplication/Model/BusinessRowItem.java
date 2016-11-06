package com.hackaton.hevre.clientapplication.Model;

import java.io.Serializable;

/**
 * Created by Ron on 06/11/2016.
 */

public class BusinessRowItem implements Serializable {

    private String mName;
    private String mAddress;
    private double longitude;
    private double latitude;

    public BusinessRowItem(String name, String address, double lng, double lat) {
        this.mName = name;
        this.mAddress = address;
        this.longitude = lng;
        this.latitude = lat;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return mAddress;
    }

    /**
     *
     * @return
     */
    public double getLng() {
        return longitude;
    }

    /**
     *
     * @return
     */
    public double getLat() {
        return latitude;
    }
}
