package com.hackaton.hevre.clientapplication.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.hevre.clientapplication.Model.Business;
import com.hackaton.hevre.clientapplication.Model.ILocationModelService;
import com.hackaton.hevre.clientapplication.Model.LocationModelService;
import com.hackaton.hevre.clientapplication.Model.TaskingStatus;
import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCallbackActivity implements OnItemClickListener {

    ILocationModelService mLocationService;
    String mUserName;
    ArrayList<String> mTasks;
    ListView mListView;
    ArrayAdapter<String> mAdapter;
    int mNotificationId = -1;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* set this activity to be delegated by two model classes */
        mLocationService = new LocationModelService(this);
        mLocationService.setDelegate(this);

        /* get the user name connected to the application */
        Bundle b = getIntent().getExtras();
        if(b != null) {
            mUserName = b.getString("user");
        }

        setActionBarTitle(getString(R.string.homeActivity_title));

        mTasks = new ArrayList<String>();

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        mAdapter.setNotifyOnChange(true);

        mListView = (ListView) findViewById(R.id.task_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mModelService.getUserTaskList(mUserName);

        mProgressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        mProgressBar.setVisibility(View.GONE);

    }

    private int getNextNotificationId()
    {
        mNotificationId++;
        return mNotificationId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            Toast.makeText(getBaseContext(), R.string.homeActivity_emptyTaskAlert, Toast.LENGTH_LONG).show();
        }
        else
        {
            mProgressBar.setVisibility(View.VISIBLE);

            (new HomeActivity.TagsGetter(this, mUserName, task)).execute();
        }
    }

    public void addtask_callback(TaskingStatus status, List<String> categories)
    {
        mProgressBar.setVisibility(View.GONE);
        String categoriesStr = categories.toString();

        Toast.makeText(getBaseContext(), categoriesStr, Toast.LENGTH_LONG).show();

        String strMsg = getString(R.string.homeActivity_taskAdded);
        if(status.equals(TaskingStatus.ILLEGAL_TASK))
        {
            strMsg = getString(R.string.homeActivity_illegalTaskAlert);
        }
        else if(status.equals(TaskingStatus.TASK_ALREADY_EXISTS))
        {
            strMsg=getString(R.string.homeActivity_taskExistsAlert);
        }
        ((EditText) findViewById(R.id.taskadd_editText)).setText("");
        //Toast.makeText(getBaseContext(), strMsg, Toast.LENGTH_LONG).show();
    }

    public void UserProducts_callback(List<String> userProducts) {
        mTasks.clear();
        mTasks.addAll(userProducts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        // show another view with businesses locations
        Location locations = mLocationService.getCurrentLocation();

        if(locations != null){
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();

            Intent intent = new Intent(HomeActivity.this, TaskActivity.class);
            intent.putExtra("taskName", ((TextView) view).getText());
            intent.putExtra("lng", longitude);
            intent.putExtra("lat", latitude);
            startActivity(intent);
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
            // calls the function that will check for relevant close businesses
            mModelService.findRelevantBusinesses(mUserName, location);
        }
    }

    /* gets all the nearest relevant businesses for the user by his location and sends notification */
    public void pushNotification_callback(ArrayList<Business> relevantBusinesses) {
        for (int i = 0; i < relevantBusinesses.size(); i++)
        {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.app_icon_white)
                            .addAction(R.drawable.notification_template_icon_bg, getString(R.string.push_notification_not_now), null)
                            .addAction(R.drawable.notification_template_icon_bg, getString(R.string.push_notification_buying), null)
                            .setContentTitle(getString(R.string.push_notification_title))
                            .setContentText(relevantBusinesses.get(0).getName() + " is only " + relevantBusinesses.get(0).getLocation().distanceTo(mLocationService.getCurrentLocation()) + " meters away!");
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            //mNotifyMgr.notify(getNextNotificationId(), mBuilder.build());
        }
    }

    public class TagsGetter extends AsyncTask<String, Void, String> {

        /* data members */
        String userName;
        String product;
        Context context;

        public TagsGetter(Context context, String userName, String product) {
            this.userName = userName;
            this.product = product;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            List<String> categories = mModelService.addProduct(userName, product);
            //mModelService.getUserTaskList(mUserName);

            String result = stringJoin(categories, ", ");
            return result;
        }

        private String stringJoin(List<String> list, String delimiter) {
            StringBuilder str = new StringBuilder();

            for (String string: list) {
                str.append(string + delimiter);
            }

            String result = str.toString().substring(0, str.toString().length() - 2);

            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            List<String> categories = Arrays.asList(resp.split("\\s*,\\s*"));
            String status = categories.get(categories.size() - 1);
            addtask_callback(TaskingStatus.valueOf(status), categories);
            mModelService.getUserTaskList(userName);
        }
    }
}