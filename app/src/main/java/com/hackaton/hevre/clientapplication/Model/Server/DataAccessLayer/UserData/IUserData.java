package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.UserData;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement.User;

/**
 * Created by אביחי on 01/03/2017.
 */

public interface IUserData {

    LoginStatus addUser(String userName, String password, String email);

    User getUserByName(String userName);

    User getFacebookUserByEmail(String email);

    LoginStatus isUserNotExist(String userName, String email);

    boolean addProduct(String userName, Product product);
}
