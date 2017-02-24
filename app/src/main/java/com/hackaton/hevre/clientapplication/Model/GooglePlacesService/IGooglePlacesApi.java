package com.hackaton.hevre.clientapplication.Model.GooglePlacesService;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;

import java.util.ArrayList;

/**
 * Created by אביחי on 24/02/2017.
 */

public interface IGooglePlacesApi {

    /**
     * returns the url of the get request filled with the given arguments
     *
     * @param longitude center longitude
     * @param latitude  center latitude
     * @param radius    the radius to take from the center coordinates
     * @return String url ready to be requested
     */
    String buildGmapsRequest(double longitude, double latitude, int radius);

    ArrayList<Business> getBusinesses(String responseString);

    String get(String urlReq);

}
