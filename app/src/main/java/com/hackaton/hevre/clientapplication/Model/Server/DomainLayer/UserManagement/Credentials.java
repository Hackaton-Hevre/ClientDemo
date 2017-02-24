package com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.UserManagement;

/**
 * Created by אביחי on 23/10/2016.
 */
public class Credentials {

    private String UserName;
    private String Password;
    private String Email;

    public Credentials(String UserName, String Password, String Email)
    {
        this.UserName = UserName;
        this.Password = Password;
        this.Email = Email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean checkCredentials(String password) {
        return Password.equals(password);
    }
}
