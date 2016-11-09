package com.hackaton.hevre.clientapplication.Communication;

import com.hackaton.hevre.clientapplication.Model.Business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ron on 07/11/2016.
 */

public class GoogleApiWrapper extends GetRequest {

    /* constants */
    private static final String GMAPS_SEARCH_REQ_PATT =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=%d";
    private static final String OPTIONAL_TYPE_TO_REQUEST =
            "&type=%s";
    private static final String API_KEY_PARAM = "&key=";

    private static final String LOCATION_PATT = "%f,%f";

    // google get request interaction
    private static final String GMAPS_API_KEY = "AIzaSyCo5XDOpitttqGTflMxu6RPXVOwqFCjc1M";
    private static final String RESP_RESULTS_KEY = "results";
    private static final String RESP_GEO_KEY = "geometry";
    private static final String RESP_LOC_KEY = "location";
    private static final String RESP_LAT_KEY = "lat";
    private static final String RESP_LNG_KEY = "lng";
    private static final String RESP_NAME_KEY = "name";
    private static final String RESP_ID_KEY = "place_id";
    private static final String RESP_ADDRESS_KEY = "vicinity";
    private static final String RESP_TYPES_KEY = "types";

    /* static */
    static GoogleApiWrapper instance = null;

    /* data members */
    // currently not!

    /**
     * @return GoogleApiWrapper object
     */
    public static GoogleApiWrapper getInstance() {
        if (instance == null) {
            instance = new GoogleApiWrapper();
        }
        return instance;
    }

    /* C-tors */

    /**
     * Default private C-tor
     */
    private GoogleApiWrapper() {

    }

    /**
     * returns the url of the get request filled with the given arguments
     *
     * @param longitude center longitude
     * @param latitude  center latitude
     * @param radius    the radius to take from the center coordinates
     * @param type      focus the search on specific type
     * @return String url ready to be requested
     */
    public String buildGmapsRequest(double longitude, double latitude, int radius, String type) {
        String req = buildGmapsRequest(latitude, longitude, radius);
        return req.concat(String.format(Locale.getDefault(), OPTIONAL_TYPE_TO_REQUEST, type));
    }

    /**
     * returns the url of the get request filled with the given arguments
     *
     * @param longitude center longitude
     * @param latitude  center latitude
     * @param radius    the radius to take from the center coordinates
     * @return String url ready to be requested
     */
    public String buildGmapsRequest(double longitude, double latitude, int radius) {
        String locationStr = String.format(LOCATION_PATT, latitude, longitude);
        String result = String.format(GMAPS_SEARCH_REQ_PATT, locationStr, radius).
                concat(API_KEY_PARAM).concat(GMAPS_API_KEY);

        return result;
    }

    public ArrayList<Business> getBusinesses(String responseString) {
        if (responseString == "")
            return new ArrayList<>();

        if (responseString == null) {
            System.out.println("response String is null!!!!!!!!!");
            return new ArrayList<>();
        }
        try {
            JSONObject resJson = new JSONObject(responseString);
            JSONArray results = resJson.getJSONArray(RESP_RESULTS_KEY);
            ArrayList<Business> businesses = new ArrayList<>(results.length());
            for (int i = 0; i < results.length(); i++) {
                JSONObject businessJson = results.getJSONObject(i);
                // fetch location
                JSONObject geometry = businessJson.getJSONObject(RESP_GEO_KEY);
                JSONObject location = geometry.getJSONObject(RESP_LOC_KEY);
                double lat = location.getDouble(RESP_LAT_KEY);
                double lng = location.getDouble(RESP_LNG_KEY);
                // fetch demographic info
                String name = businessJson.getString(RESP_NAME_KEY);
                String address = businessJson.getString(RESP_ADDRESS_KEY);
                String id = businessJson.getString(RESP_ID_KEY);
                // fetch place types of service
                JSONArray typesJson = businessJson.getJSONArray(RESP_TYPES_KEY);
                String[] types = new String[typesJson.length()];
                for (int j = 0; j < typesJson.length(); j++) {
                    types[j] = typesJson.getString(j);
                }
                Business b = (new Business.BusinessBuilder()).
                        setId(id).setName(name).
                        setTypes(types).
                        setLocation(lat, lng).
                        setAddress(address).
                        build();
                businesses.add(b);
            }
            return businesses;
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println(e.getCause());
        }
        return new ArrayList<Business>();

    }
}
