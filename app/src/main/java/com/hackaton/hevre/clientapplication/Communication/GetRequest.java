package com.hackaton.hevre.clientapplication.Communication;
/**
 * Created by אביחי on 30/09/2016.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An AsyncTask implementation for performing GETs on the Hypothetical REST APIs.
 */
public class GetRequest<T> extends AsyncTask<String, Void, String> {

    /* constants */
    private static final int OK_STATUS_CODE = 200;
    private static final String MSG_UNSUCCESS = "Unsuccessful request: %s, ststus code: %d";

    /* data members */
    private String requestUrl;
    private RestTaskCallback mCallback;

    /**
     * Creates a new instance of GetTask with the specified URL and callback.
     *
     * @param requestUrl The URL represent the GET request.
     * @param callback The callback to be invoked when the HTTP request
     *            completes.
     *
     */
    public GetRequest(String requestUrl, RestTaskCallback callback){
        this.requestUrl = requestUrl;
        this.mCallback = callback;
    }

    private String convertInputStreamToString(InputStream inputStream)
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            //inputStream.close();
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Problem has occured";
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        //Use HTTP Client APIs to make the call.
        //Return the HTTP Response body here.
        // create HttpClient
        InputStream inputStream = null;
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(this.requestUrl));
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != OK_STATUS_CODE)
                System.err.println(String.format(MSG_UNSUCCESS, this.requestUrl, statusCode));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                response = convertInputStreamToString(inputStream);
            else
                response = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
//        mCallback.onTaskComplete(result);
        super.onPostExecute(result);
    }
}