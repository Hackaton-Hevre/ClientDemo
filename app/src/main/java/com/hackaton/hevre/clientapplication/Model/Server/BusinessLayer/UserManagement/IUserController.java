package com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.UserManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;

import java.util.List;

/**
 * Created by אביחי on 01/03/2017.
 */

public interface IUserController {

    boolean addProduct(String userName, Product product);

    LoginStatus addUser(String userName, String password, String email);

    LoginStatus login(String userName, String password);

    List<String> getUserTaskList(String userName);

    LoginStatus facebookLogin(String facebookKey, String email);
}
