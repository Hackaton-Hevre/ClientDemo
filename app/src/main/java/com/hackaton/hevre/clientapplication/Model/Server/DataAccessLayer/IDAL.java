package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer;

import android.content.Context;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;

import java.util.ArrayList;

/**
 * Created by אביחי on 24/02/2017.
 */

public interface IDAL {

    void open();

    void close();

    void setOpenHelper(Context context);

    ArrayList<Business> getBusinessesInRange(double lon, double lat, double radius);
}
