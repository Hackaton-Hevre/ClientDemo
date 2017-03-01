package com.hackaton.hevre.clientapplication.Model.Server.DataAccessLayer.UserData;

import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.ProductManagement.Product;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement.User;

import java.util.HashMap;

/**
 * Created by אביחי on 01/03/2017.
 */

public class UserData implements IUserData {

    private static UserData instance = null;
    // username to users
    HashMap<String, User> mUsers;
    // mail to users
    HashMap<String, User> mUsersByEmail;

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

        // add some static data
        addUser("avichay", "12345", "avichay13@gmail.com");
        addUser("aviv", "12345", "avivalush@gmail.com");
        addUser("ron", "12345", "ronpick@gmail.com");
        addUser("moshe", "12345", "moshenoi@gmail.com");
        addUser("ran", "12345", "emuna.ran@gmail.com");
        addUser("itamar", "12345", "itamar.cohen@gmail.com");
        addUser("rozbaum", "12345", "adi.rozen@gmail.com");
    }

    @Override
    public LoginStatus addUser(String userName, String password, String email)
    {
        LoginStatus status = LoginStatus.SUCCESS;

        User user = new User(userName, password, email.toLowerCase());

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
            User user = mUsers.get(userName);
            isAdded = user.user_addProduct(product);
        }

        return isAdded;
    }

    @Override
    public User getUserByName(String userName) {
        User user = null;

        if (mUsers.containsKey(userName))
        {
            user = mUsers.get(userName);
        }

        return user;
    }

    @Override
    public User getFacebookUserByEmail(String email) {
        return null;
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
