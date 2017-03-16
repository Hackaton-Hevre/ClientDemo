package com.hackaton.hevre.clientapplication.Model.Server.BusinessLayer.UserManagement;

import com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.UserData.IUserData;
import com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.UserData.UserData;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement.IUser;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 */
public class UserController implements IUserController {

    private static UserController instance = null;
    private IUserData mUserData;

    public static UserController getInstance()
    {
        if (instance == null)
        {
            instance = new UserController();
        }
        return instance;
    }

    private UserController()
    {
        mUserData = UserData.getInstance();
    }

    // TODO - add support for informative ERROR MESSAGE
    public boolean addProduct(String userName, Product product)
    {
        return mUserData.addProduct(userName, product);
    }

    public LoginStatus addUser(String userName, String password, String email)
    {
        LoginStatus status = mUserData.isUserNotExist(userName, password);

        /*LoginStatus status = LoginStatus.SUCCESS;

        if (mUsers.containsKey(userName))
        {
            status = LoginStatus.NAME_ALREADY_EXISTS;
        }
        else if (mUsersByEmail.containsKey(email))
        {
            status = LoginStatus.MAIL_ALREADY_EXIST;
        }
        else
        {
            User user = new User(userName, password, email);
            mUsers.put(userName, user);
            mUsersByEmail.put(email, user);
        }*/

        if (status.equals(LoginStatus.SUCCESS))
        {
            status = mUserData.addUser(userName, password, email);
        }

        return status;
    }

    public LoginStatus login(String userName, String password)
    {
        LoginStatus status = LoginStatus.SUCCESS;
        IUser user = mUserData.getUserByName(userName);

        if (user == null)
        {
            status = LoginStatus.NAME_DOESNT_EXIST;
        }
        else if (!user.checkCredentials(password))
        {
            status = LoginStatus.INVALID_CREDENTIALS;
        }
        return status;
    }

    @Override
    public LoginStatus facebookLogin(String facebookKey, String email) {
        LoginStatus status = LoginStatus.FACEBOOK_LOGIN;
        IUser user = mUserData.getFacebookUserById(facebookKey);

        if (user == null)
        {
            status = mUserData.addFacebookUser(facebookKey, email);
            if (status.equals(LoginStatus.SUCCESS))
            {
                user = mUserData.getFacebookUserById(facebookKey);
            }
        }

        if (user != null && !user.checkCredentials(facebookKey))
        {
            status = LoginStatus.ERROR;
        }

        if (status.equals(LoginStatus.SUCCESS))
        {
            status = LoginStatus.FACEBOOK_LOGIN;
        }

        return status;
    }

    public List<String> getUserTaskList(String userName) {
        IUser user = mUserData.getUserByName(userName);
        List<String> userProductsByName = new LinkedList<>();

        if (user != null)
        {
            userProductsByName = user.getStringProducts();
        }
        return userProductsByName;
    }
}
