package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

import java.util.LinkedList;

/**
 * Created by אביחי on 13/03/2017.
 */

public class FacebookUser extends IUser {

    public FacebookUser (String facebookId, String email){
        mCredentials = new FacebookCredentials(facebookId, email);
        mActiveProducts = new LinkedList<Product>();
        mInActiveProducts = new LinkedList<Product>();
    }
}
