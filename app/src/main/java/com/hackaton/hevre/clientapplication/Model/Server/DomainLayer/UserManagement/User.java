package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

import java.util.LinkedList;


/**
 * Created by אביחי on 23/10/2016.
 */
public class User extends IUser {

    public User (String UserName, String Password, String Email){
        mCredentials = new Credentials(UserName, Password, Email);
        mActiveProducts = new LinkedList<Product>();
        mInActiveProducts = new LinkedList<Product>();
    }

}
