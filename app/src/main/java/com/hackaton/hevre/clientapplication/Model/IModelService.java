package com.hackaton.hevre.clientapplication.Model;

import android.app.Activity;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by אביחי on 23/10/2016.
 */
public interface IModelService {

    /**
     * Sets the model delegate - supporting the MVC design pattern.
     *
     * @param activity - the activity that now holds the modelInstance and will get the callbacks
     */
    void setDelegate(Activity activity);

    /**
     * Connects the user to the system.
     *
     * @param userName - the username
     * @param password - the password
     */
    void login(String userName, String password);

    /**
     * Do a registration for a new user.
     *
     * @param userName - the username
     * @param password - the password
     * @param email - the email
     */
    void register(String userName, String password, String email);

    /**
     * Adding a new product for a user.
     *
     * @param userName - the username
     * @param productName - the password
     */
    void addProduct(String userName, String productName);

    /**
     * Look for relevant businesses for user and location (by the user's tasks) and send push notifications.
     *
     * @param username - states who is the user
     * @param location - the location where the user is now
     */
    void findRelevantBusinesses(String username, Location location);

    /**
     * Finds all the tasks for a user.
     *
     * @param userName - states who is the user
     */
    void getUserTaskList(String userName);
    
    /**
     * Gets all the businesses in range radius.
     *  @param longitude - the longitude
     * @param latitude - the latitude
     * @param radius - the radius
     */
    ArrayList<BusinessRowItem> getBusinessesInRange(double longitude, double latitude, double radius);


    /**
     * Close the database.
     *
     */
    void closeDb();
}
