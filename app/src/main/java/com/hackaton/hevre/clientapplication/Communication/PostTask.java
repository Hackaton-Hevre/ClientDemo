package com.hackaton.hevre.clientapplication.Communication;

/**
 * Created by אביחי on 30/09/2016.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * An AsyncTask implementation for performing POSTs on the Hypothetical REST APIs.
 */
public class PostTask<T> extends AsyncTask<T, T, T> {
    private String mRestUrl;
    private RestTaskCallback mCallback;
    private String mRequestBody;

    /**
     * Creates a new instance of PostTask with the specified URL, callback, and
     * request body.
     *
     * @param restUrl The URL for the REST API.
     * @param callback The callback to be invoked when the HTTP request
     *            completes.
     * @param requestBody The body of the POST request.
     *
     */
    public PostTask(String restUrl, String requestBody, RestTaskCallback callback){
        this.mRestUrl = restUrl;
        this.mRequestBody = requestBody;
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
    protected T doInBackground(T... arg0) {
        //Use HTTP client API's to do the POST
        //Return response.
        T response = null;
        //Use HTTP Client APIs to make the call.
        //Return the HTTP Response body here.
        // create HttpClient
        InputStream inputStream = null;
        try {

            // create HttpClient
            HttpClient httpClientlient = new DefaultHttpClient();

            HttpPost post = new HttpPost(this.mRestUrl);

            HttpResponse httpResponse = httpClientlient.execute(post);

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