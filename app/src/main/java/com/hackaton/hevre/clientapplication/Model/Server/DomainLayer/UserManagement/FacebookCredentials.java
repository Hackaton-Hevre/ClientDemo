package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement;

/**
 * Created by אביחי on 13/03/2017.
 */

public class FacebookCredentials implements ICredentials {

    private String mFacebookId;
    private String mEmail;

    public FacebookCredentials(String facebookId, String email)
    {
        mFacebookId = facebookId;
        mEmail = email;
    }

    @Override
    public boolean checkCredentials(String facebookId) {
        return mFacebookId.equals(facebookId);
    }
}
