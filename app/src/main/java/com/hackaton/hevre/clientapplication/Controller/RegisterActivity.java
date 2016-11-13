package com.hackaton.hevre.clientapplication.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapplication.Model.LoginStatus;
import com.hackaton.hevre.clientapplication.R;


public class RegisterActivity extends AppCallbackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setActionBarTitle(getString(R.string.registerActivity_title));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void registerRegButton_onClick(View view)
    {
        String username = ((TextView) findViewById(R.id.username_text_re)).getText().toString();
        String email = ((TextView) findViewById(R.id.Email_text_re)).getText().toString();
        String password = ((TextView) findViewById(R.id.password_text_re)).getText().toString();
        String repassword = ((TextView) findViewById(R.id.repassword_text_re)).getText().toString();

        if(checkRegisterInput(username,email,password,repassword))
        {
            mModelService.register(username, password, email);
        }
    }

    // this happens when getting an answer from login
    public void register_callback(LoginStatus status)
    {
        if(status.equals(LoginStatus.NAME_ALREADY_EXISTS))
        {
            Toast.makeText(getBaseContext(), "User name is already exist", Toast.LENGTH_SHORT).show();
        }
        if(status.equals(LoginStatus.MAIL_ALREADY_EXIST))
        {
            Toast.makeText(getBaseContext(), "Mail is already exist", Toast.LENGTH_SHORT).show();
        }
        if(status.equals(LoginStatus.SUCCESS))
        {
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            Bundle b = new Bundle();
            String username = ((TextView) findViewById(R.id.username_text_re)).getText().toString();
            b.putString("user", username); //Your id
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();
        }
    }

    private boolean checkRegisterInput(String username, String email, String password, String repassword ) {
        StringBuilder errorMsg = new StringBuilder();
        boolean flag1 = false, flag2 = false;

        if (username.matches("^.*[^a-zA-Z0-9 ].*$") || username.isEmpty()) {
            errorMsg.append("- Username invalid\n");
        }
        if (!email.contains("@") || email.isEmpty()) {
            errorMsg.append("- Email address is invalid\n");
        }

        if (password.isEmpty()) {
            errorMsg.append("- Password is empty\n");
            flag1 = true;
        }
        if (repassword.isEmpty()) {
            errorMsg.append("- Re checking password is empty\n");
            flag2 = true;
        }
        if (flag1 == false && flag2 == false) {
            if (!password.equals(repassword))
                errorMsg.append("- Passwords are NOT equal");
        }
        String eMsg = errorMsg.toString();
        if (!eMsg.isEmpty())
        {
            eMsg = eMsg + "PLEASE INSERT CORRECT DETAILS";
            Toast.makeText(getBaseContext(), errorMsg, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
