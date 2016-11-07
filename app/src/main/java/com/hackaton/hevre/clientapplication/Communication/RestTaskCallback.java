package com.hackaton.hevre.clientapplication.Communication;

/**
 * Created by אביחי on 30/09/2016.
 */


/**
 * Class definition for a callback to be invoked when the HTTP request
 * representing the REST API Call completes.
 */

public abstract class RestTaskCallback<T>{
    /**
     * Called when the HTTP request completes.
     *
     * @param result The result of the HTTP request.
     */
    public abstract void onTaskComplete(T result);
}