package com.hackaton.hevre.clientapplication.Model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by אביחי on 23/10/2016.
 */
public class UserController {

    // username to users
    HashMap<String, User> mUsers;
    // mail to users
    HashMap<String, User> mUsersByEmail;

    public UserController()
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

    public LoginStatus addUser(String userName, String password, String email)
    {
        LoginStatus status = LoginStatus.SUCCESS;

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
        }

        return status;
    }

    public LoginStatus login(String userName, String password)
    {
        LoginStatus status = LoginStatus.SUCCESS;

        if (!mUsers.containsKey(userName))
        {
            status = LoginStatus.NAME_DOESNT_EXIST;
        }
        else if (!mUsers.get(userName).checkCredentials(password))
        {
            status = LoginStatus.INVALID_CREDENTIALS;
        }
        return status;
    }

    public List<String> getUserTaskList(String userName) {
        User user = mUsers.get(userName);
        List<String> userProductsByName = user.getStringProducts();
        return userProductsByName;
    }
}
