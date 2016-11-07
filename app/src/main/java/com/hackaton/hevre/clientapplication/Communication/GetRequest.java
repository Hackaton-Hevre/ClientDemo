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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An AsyncTask implementation for performing GETs on the Hypothetical REST APIs.
 */
public class GetRequest<T> extends AsyncTask<T, T, T> {

    private String mRestUrl;
    private RestTaskCallback mCallback;

    /**
     * Creates a new instance of GetTask with the specified URL and callback.
     *
     * @param restUrl The URL for the REST API.
     * @param callback The callback to be invoked when the HTTP request
     *            completes.
     *
     */
    public GetRequest(String restUrl, RestTaskCallback callback){
        this.mRestUrl = restUrl;
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
    protected T doInBackground(T... params) {
        T response = null;
        //Use HTTP Client APIs to make the call.
        //Return the HTTP Response body here.
        // create HttpClient
        InputStream inputStream = null;
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(this.mRestUrl));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                response = (T) convertInputStreamToString(inputStream);
            else
                response = (T) "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(T result) {
        mCallback.onTaskComplete(result);
        super.onPostExecute(result);
    }
}