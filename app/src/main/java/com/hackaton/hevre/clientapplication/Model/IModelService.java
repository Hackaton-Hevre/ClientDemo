package com.hackaton.hevre.clientapplication.Model;

import android.app.Activity;
import android.location.Location;

/**
 * Created by אביחי on 23/10/2016.
 */
public interface IModelService {

    public void setDelegate(Activity activity);

    public void login(String userName, String password);

    public void register(String userName, String password, String email);

    public void addProduct(String userName, String productName);

    public void findReleventBusinesses(String username, Location location);

    public void getUserTaskList(String userName);
}
