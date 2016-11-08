package com.hackaton.hevre.clientapplication.Communication;

import android.annotation.TargetApi;
import android.os.Build;

import com.hackaton.hevre.clientapplication.Model.Business;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Ron on 07/11/2016.
 */

public class GoogleApiWrapper {

    /* constants */
    private static final String GMAPS_SEARCH_REQ_PATT =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=%d";
    private static final String OPTIONAL_TYPE_TO_REQUEST =
            "&type=%s";
    private static final String API_KEY_PARAM = "&key=";

    private static final String LOCATION_PATT = "%f,%f";

    // response issues
    private static final int OK_STATUS_CODE = 200;
    private static final String MSG_UNSUCCESS = "Unsuccessful request: %s, ststus code: %d";
    private static final String USER_AGENT = "Mozilla/5.0;";

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

    /**
     * helps return the response as String.
     *
     * @param inputStream InputStream object.
     * @return String represent the given inputStream.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String convertInputStreamToString(InputStream inputStream) {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            StringBuilder resultString = new StringBuilder(2048);  // large capacity for efficiency
            while ((line = bufferedReader.readLine()) != null)
                resultString.append(line);

            return resultString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Problem has occured";
        }
    }

    public String get(String urlReq) {
        System.out.println("---\tget()\t---");
        HttpURLConnection connection;
        String responseString;
        InputStream responseStream;
        try {
            URL url = new URL(urlReq);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setRequestProperty("User-Agent", USER_AGENT);
//            connection.setConnectTimeout(500);
            connection.connect();

            int statusCode = connection.getResponseCode();
            System.out.println("Response Code: " + connection.getResponseCode());
            if (statusCode != OK_STATUS_CODE) {
                System.err.println(String.format(Locale.US, MSG_UNSUCCESS, urlReq, statusCode));
                return "";
            }

            responseStream = connection.getInputStream();
            if (responseStream != null)
                responseString = convertInputStreamToString(responseStream);
            else
                responseString = "";
        } catch (Exception e) {
            System.err.println(e.getCause());
            e.printStackTrace();
            responseString = "";
        }
        return responseString;
    }

    public ArrayList<Business> getBusinesses(String responseString) {
        if (responseString == "")
            return new ArrayList<>();

        if (responseString == null) {
            System.out.println("response String is null!!!!!!!!!");
            return new ArrayList<>();
        } else {
            System.out.println("this is the response:\n" + responseString + "\n\n");
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
