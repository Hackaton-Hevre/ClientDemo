package com.hackaton.hevre.clientapplication.Model;

import android.app.Activity;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by אביחי on 23/10/2016.
 */
public interface IModelService {

    void setDelegate(Activity activity);

    void login(String userName, String password);

    void register(String userName, String password, String email);

    void addProduct(String userName, String productName);

    void findReleventBusinesses(String username, Location location);

    void getUserTaskList(String userName);

    ArrayList<String> getBusinessesInRange(double longitude, double latitude, double v);

    void closeDb();
}
