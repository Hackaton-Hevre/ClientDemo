package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.UserData;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement.FacebookUser;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement.IUser;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement.User;

import java.util.HashMap;

/**
 * Created by אביחי on 01/03/2017.
 */

public class UserData implements IUserData {

    private static UserData instance = null;
    // username to users
    HashMap<String, IUser> mUsers;
    // mail to users
    HashMap<String, IUser> mUsersByEmail;
    // facebookId to users
    HashMap<String, IUser> mFacebookUsers;

    public static UserData getInstance()
    {
        if (instance == null)
        {
            instance = new UserData();
        }
        return instance;
    }

    private UserData()
    {
        mUsers = new HashMap<>();
        mUsersByEmail = new HashMap<>();
        mFacebookUsers = new HashMap<>();

        // add some static data
        addUser("avichay", "12345", "avichay13@gmail.com");
        addUser("aviv", "12345", "avivalush@gmail.com");
        addUser("ron", "12345", "ronpick@gmail.com");
        addUser("moshe", "12345", "moshenoi@gmail.com");
        addUser("ran", "12345", "emuna.ran@gmail.com");
        addUser("itamar", "12345", "itamar.cohen@gmail.com");
        addUser("rozbaum", "12345", "adi.rozen@gmail.com");
        addFacebookUser("10158180273220136", "avichay13@gmail.com");
    }

    @Override
    public LoginStatus addUser(String userName, String password, String email)
    {
        LoginStatus status = LoginStatus.SUCCESS;

        IUser user = new User(userName, password, email.toLowerCase());

        try
        {
            mUsers.put(userName, user);
            mUsersByEmail.put(email.toLowerCase(), user);
        }
        catch (Exception e)
        {
            status = LoginStatus.ERROR;
        }

        return status;
    }

    @Override
    public boolean addProduct(String userName, Product product)
    {
        boolean isAdded = false;

        if (mUsers.containsKey(userName))
        {
            IUser user = mUsers.get(userName);
            isAdded = user.user_addProduct(product);
        }

        return isAdded;
    }

    @Override
    public LoginStatus addFacebookUser(String facebookKey, String email) {
        LoginStatus status = LoginStatus.SUCCESS;

        IUser user = new FacebookUser(facebookKey, email.toLowerCase());

        try
        {
            mUsers.put(facebookKey, user);
            mFacebookUsers.put(facebookKey, user);
            mUsersByEmail.put(email.toLowerCase(), user);
        }
        catch (Exception e)
        {
            status = LoginStatus.ERROR;
        }

        return status;
    }

    @Override
    public IUser getUserByName(String userName) {
        IUser user = null;

        if (mUsers.containsKey(userName))
        {
            user = mUsers.get(userName);
        }

        return user;
    }

    @Override
    public IUser getFacebookUserById(String id) {
        IUser user = null;

        if (mFacebookUsers.containsKey(id))
        {
            user = mFacebookUsers.get(id);
        }

        return user;
    }

    @Override
    public LoginStatus isUserNotExist(String userName, String email) {
        LoginStatus status = LoginStatus.SUCCESS;

        if (mUsers.containsKey(userName))
        {
            status = LoginStatus.NAME_ALREADY_EXISTS;
        }
        else if (mUsersByEmail.containsKey(email.toLowerCase()))
        {
            status = LoginStatus.MAIL_ALREADY_EXIST;
        }

        return status;
    }

}
