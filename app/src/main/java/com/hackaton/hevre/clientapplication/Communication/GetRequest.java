package com.hackaton.hevre.clientapplication.Communication;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by אביחי on 30/09/2016.
 */

public class GetRequest {

    // response issues
    private static final int OK_STATUS_CODE = 200;
    private static final String MSG_UNSUCCESS = "Unsuccessful request: %s, ststus code: %d";
    private static final String USER_AGENT = "Mozilla/5.0;";

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
        HttpURLConnection connection;
        String responseString;
        InputStream responseStream;
        try {
            URL url = new URL(urlReq);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
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

}