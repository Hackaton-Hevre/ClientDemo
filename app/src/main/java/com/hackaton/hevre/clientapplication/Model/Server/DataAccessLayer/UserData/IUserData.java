package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.UserData;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement.IUser;

/**
 * Created by אביחי on 01/03/2017.
 */

public interface IUserData {

    LoginStatus addUser(String userName, String password, String email);

    IUser getUserByName(String userName);

    IUser getFacebookUserById(String email);

    LoginStatus isUserNotExist(String userName, String email);

    boolean addProduct(String userName, Product product);

    LoginStatus addFacebookUser(String facebookKey, String email);
}
