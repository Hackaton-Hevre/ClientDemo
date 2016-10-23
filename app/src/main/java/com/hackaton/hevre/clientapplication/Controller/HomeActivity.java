package com.hackaton.hevre.clientapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hackaton.hevre.clientapplication.Model.IModelService;
import com.hackaton.hevre.clientapplication.Model.ModelService;
import com.hackaton.hevre.clientapplication.Model.TaskingStatus;
import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity {

    IModelService mModelService = ModelService.getInstance();
    String mUserName;
    ArrayList<String> mTasks;
    ListView mListView;
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mModelService.setDelegate(this);
        Bundle b = getIntent().getExtras();
        if(b != null) {
            mUserName = b.getString("user");
        }

        mTasks = new ArrayList<String>();

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        mAdapter.setNotifyOnChange(true);

        mListView = (ListView) findViewById(R.id.task_list);
        mListView.setAdapter(mAdapter);

        mModelService.getUserTaskList(mUserName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void addtask_onclick(View view) {
        String task = ((EditText) findViewById(R.id.taskadd_editText)).getText().toString();
        if (task.equals(""))
        {
            Toast.makeText(getBaseContext(), "Empty Task, pleas enter new one!", Toast.LENGTH_LONG).show();
        }
        else
        {
            mModelService.addProduct(mUserName, task);
            mModelService.getUserTaskList(mUserName);
        }
    }

    public void addtask_callback(TaskingStatus status)
    {
        String strMsg="OK, Task has been added!";
        if(status.equals(TaskingStatus.ILLEGAL_TASK))
        {
            strMsg="ILLEGAL TASK, task has not been found";
        }
        else if(status.equals(TaskingStatus.TASK_ALREADY_EXISTS))
        {
            strMsg="your TASK is already exist";
        }
        ((EditText) findViewById(R.id.taskadd_editText)).setText("");
        Toast.makeText(getBaseContext(), strMsg, Toast.LENGTH_LONG).show();
    }

    public void UserProducts_callback(List<String> userProducts) {
        mTasks.clear();
        mTasks.addAll(userProducts);
        mAdapter.notifyDataSetChanged();
    }

    public void logout_onclick(MenuItem item) {
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MAIN_ACTIVITY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("rememberMe", false);
        editor.commit();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
