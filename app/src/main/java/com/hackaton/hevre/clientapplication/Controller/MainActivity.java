package com.hackaton.hevre.clientapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.LoginStatus;
import com.hackaton.hevre.clientapplication.R;

import java.util.Arrays;


public class MainActivity extends AppCallbackActivity {

    public static final String MAIN_ACTIVITY_PREFERENCES = "main_activity_preferences";
    EditText mUsernameEdit;
    EditText mPasswordEdit;
    LoginButton loginButton;
    CheckBox mRememberMeCheckBox;
    CallbackManager mCallbackManager;
    AnaleadFacebookCallback mFacebookCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        setActionBarTitle(getString(R.string.mainActivity_title));

        initUiElements();

        autoLogin();
    }

    private void initUiElements() {
        mUsernameEdit = (EditText) findViewById(R.id.username_text);
        mPasswordEdit = (EditText) findViewById(R.id.password_text);
        mRememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMe_checkBox);
        mRememberMeCheckBox.setChecked(true);
        initFacebookButton();
    }

    private void initFacebookButton() {
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookCallback = new AnaleadFacebookCallback(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        LoginManager.getInstance().registerCallback(mCallbackManager, mFacebookCallback);
        loginButton.registerCallback(mCallbackManager, mFacebookCallback);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // this method checks if it can login without username and password from the user - if it can so login
    private void autoLogin() {
        SharedPreferences sharedpreferences = getSharedPreferences(MAIN_ACTIVITY_PREFERENCES, Context.MODE_PRIVATE);

        String profileId = sharedpreferences.getString("facebookProfileId", "");
        if (profileId != null && profileId != "")
        {
            String profileEmail = sharedpreferences.getString("facebookProfileEmail", "");
            mModelService.facebookLogin(profileId, profileEmail);
        }
        else {
            boolean rememberMe = sharedpreferences.getBoolean("rememberMe", false);
            String username = sharedpreferences.getString("username", "");
            String password = sharedpreferences.getString("password", "");

            mUsernameEdit.setText(username);
            if (rememberMe && !username.equals("") && !password.equals(""))
            {
                mModelService.login(username, password);
            }
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        SharedPreferences sharedpreferences = getSharedPreferences(MAIN_ACTIVITY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if (mFacebookCallback.getCurrentProfile() != null)
        {
            editor.putString("facebookProfileId",mFacebookCallback.getCurrentProfile().getId());
            editor.putString("facebookProfileEmail", mFacebookCallback.getCurrentEmail());
        }
        else if (mRememberMeCheckBox.isChecked())
        {
            editor.putString("username", mUsernameEdit.getText().toString());
            editor.putString("password", mPasswordEdit.getText().toString());
        }
        else
        {
            editor.putString("username", mUsernameEdit.getText().toString());
            editor.putString("password", "");
        }
        editor.putBoolean("rememberMe", mRememberMeCheckBox.isChecked());
        editor.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // handles click on login
    public void loginButton_onClick(View view) {
        String userName = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        if (userName.equals("") || password.equals("")) {
            Toast.makeText(getBaseContext(), R.string.mainActivity_fillAll, Toast.LENGTH_LONG).show();
        } else {
            mModelService.login(userName, password);
        }
    }

    // handles click on register
    public void registerButton_onClick(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        onPause();
    }

    // gets the answer for login fuction
    public void loginCallback(LoginStatus status) {
        if (status.equals(LoginStatus.INVALID_CREDENTIALS))
        {
            Toast.makeText(getBaseContext(), R.string.mainActivity_wrongPassword, Toast.LENGTH_LONG).show();
        }
        else if (status.equals(LoginStatus.NAME_DOESNT_EXIST))
        {
            Toast.makeText(getBaseContext(), R.string.mainActivity_wrongUsername, Toast.LENGTH_LONG).show();
        }
        else if (status.equals(LoginStatus.ERROR))
        {
            Toast.makeText(getBaseContext(), R.string.mainActivity_loginError, Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            Bundle b = new Bundle();
            String username = "";
            if (status.equals(LoginStatus.FACEBOOK_LOGIN))
            {
                try
                {
                    username = mFacebookCallback.getCurrentProfile().getId();
                }
                catch (Exception e)
                {
                    // work around for now
                    Toast.makeText(this, "This is an error for now since we don't have a data base and your last visit here was with facebook login - please login again", Toast.LENGTH_LONG);
                    return;
                    /*LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(
                            "public_profile", "email", "user_birthday", "user_friends"));*/
                }
            }
            else {
                username = mUsernameEdit.getText().toString();
            }
            b.putString("user", username); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();
        }
    }

    public void facebookLoginSuccess(String email) {
        Profile profile = mFacebookCallback.getCurrentProfile();
        mModelService.facebookLogin(profile.getId(), email);
    }
}