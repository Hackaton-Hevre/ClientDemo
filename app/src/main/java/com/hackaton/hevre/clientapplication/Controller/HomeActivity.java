package com.hackaton.hevre.clientapplication.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapplication.Model.Business;
import com.hackaton.hevre.clientapplication.Model.ILocationModelService;
import com.hackaton.hevre.clientapplication.Model.IModelService;
import com.hackaton.hevre.clientapplication.Model.LocationModelService;
import com.hackaton.hevre.clientapplication.Model.ModelService;
import com.hackaton.hevre.clientapplication.Model.Tag;
import com.hackaton.hevre.clientapplication.Model.TaskingStatus;
import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;
import java.util.List;



//import android.support.v7.app.ActionBarActivity;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener, AdapterView.OnItemLongClickListener {

    IModelService mModelService;
    ILocationModelService mLocationService;
    String mUserName;
    ArrayList<String> mTasks;
    ListView mListView;
    ArrayAdapter<String> mAdapter;
    int mNotificationId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mModelService = ModelService.getInstance(this);
        /* set this activity to be delegated by two model classes */
        mModelService.setDelegate(this);
        mLocationService = new LocationModelService(this);
        mLocationService.setDelegate(this);

        /* get the user name connected to the application */
        Bundle b = getIntent().getExtras();
        if(b != null) {
            mUserName = b.getString("user");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        mTasks = new ArrayList<String>();

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        mAdapter.setNotifyOnChange(true);

        mListView = (ListView) findViewById(R.id.task_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {


                actionMode.setTitle(mListView.getCheckedItemCount() + "selected items");
                int color = Color.WHITE;
                if (checked)
                {
                    color = Color.parseColor("#4AB6E7");
                }

                mListView.getChildAt(position).setBackgroundColor(color);
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
               // ((Toolbar)findViewById(R.id.home_toolbar)).hideOverflowMenu();
                //Log.i(this.getClass().toString(),"creating action mode");
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.my_cab_menu, menu);
             //   menu.findItem(R.id.cab_four).setVisible(false);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                for (int i = 0; i < mListView.getChildCount(); i++)
                {
                    mListView.getChildAt(i).setBackgroundColor(Color.WHITE);
                }

            }
        });
        mModelService.getUserTaskList(mUserName);

    }

    private int getNextNotificationId()
    {
        mNotificationId++;
        return mNotificationId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mModelService.setDelegate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mModelService.closeDb();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        CharSequence msg = ((TextView) view).getText();

        // show another view with businesses locations
        Location locations = mLocationService.getCurrentLocation();
        ArrayList<String> businesses = new ArrayList<>();
        if(null!=locations){
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            try {
                businesses = mModelService.getBusinessesInRange(longitude, latitude, 0.1);
                if (businesses.isEmpty()) {
                    msg = "No nearby businesses was found";
                    System.out.println("no businesses found");
                }
                else {
                    System.out.println(businesses.get(0));
                    Intent intent = new Intent(HomeActivity.this, TaskActivity.class);
                    intent.putExtra("businessesList", businesses);
                    Bundle b = new Bundle();
                    startActivity(intent);
                }
            }
            catch (NullPointerException e) {
                e.printStackTrace();
                msg = String.format("Current Location:\nlon: %s\nlat: %s", longitude, latitude);
            }
            finally {
                Intent intent = new Intent(HomeActivity.this, TaskActivity.class);
                intent.putExtra("businessesList", businesses);
                startActivity(intent);
            }
        }
    }

    /* handles the logout click */
    public void logout_onclick(MenuItem item) {
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MAIN_ACTIVITY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("rememberMe", false);
        editor.apply();
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /* handles the update of the location for the user */
    public void locationChanged_callback(Location location) {
        String msg = "";
        if(null!=location){
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            msg = String.format("Current Location:\nlon: %s\nlat: %s", longitude, latitude);
            // calls the function that will check for relevant close businesses
            mModelService.findRelevantBusinesses(mUserName, location);
        }
        //Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();

    }

    /* gets all the nearest relevant businesses for the user by his location and sends notification */
    public void pushNotification_callback(ArrayList<Business> relevantBusinesses) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Don't forget your task!")
                        .setContentText(relevantBusinesses.get(0).getName() + " is only " + relevantBusinesses.get(0).getLocation().distanceTo(mLocationService.getCurrentLocation()) + " meters away!");
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(getNextNotificationId(), mBuilder.build());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        String str = "TEST";
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
        return false;
    }
}