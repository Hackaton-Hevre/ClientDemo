package com.hackaton.hevre.clientapplication.Controller;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.NotificationCompat;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.hackaton.hevre.clientapplication.Model.LocationService.ILocationModelService;
import com.hackaton.hevre.clientapplication.Model.LocationService.LocationModelService;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.BusinessManagement.Business;
import com.hackaton.hevre.clientapplication.Model.Server.DomainLayer.Common.TaskingStatus;
import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCallbackActivity implements OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnTouchListener {

    ILocationModelService mLocationService;
    String mUserName;
    ArrayList<String> mTasks;
    ListView mListView;
    ArrayAdapter<String> mAdapter;
    int mNotificationId = -1;
    ProgressBar mProgressBar;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    boolean mIsListening;
    ImageButton mVoiceButton;

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
        mVoiceButton = (ImageButton) findViewById(R.id.search_voice_btn);

        mVoiceButton.setOnTouchListener(this);

        mTasks = new ArrayList<String>();

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTasks);
        mAdapter.setNotifyOnChange(true);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());


        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);
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

        mProgressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        // show another view with businesses locations
        Location locations = mLocationService.getCurrentLocation();//getCurrentLocation();

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

    /* on click listener for the add task button, adding a task for the user */
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

    /* callback handling the result of the add task operation */
    public void addtask_callback(TaskingStatus status, List<String> categories) {
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

    /* callback that refreshes the users tasks list */
    public void UserProducts_callback(List<String> userProducts) {
        mTasks.clear();
        mTasks.addAll(userProducts);
        mAdapter.notifyDataSetChanged();
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        String str = "TEST";
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
        return false;
    }

    /* handles the logout click */
    public void logout_onclick(MenuItem item) {
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MAIN_ACTIVITY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("rememberMe", false);
        editor.putString("facebookProfileId", "");
        editor.apply();
        try
        {
            LoginManager.getInstance().logOut();
        }
        catch (Exception e)
        {

        }
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

    /* handling the voice record click */
    public void voice_onClick(View view) {
        if (!mIsListening)
        {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        }

    }

    /* returns the next notification id */
    private int getNextNotificationId() {
        mNotificationId++;
        return mNotificationId;
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
            mNotifyMgr.notify(getNextNotificationId(), mBuilder.build());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                v.setPressed(true);
                v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom));
                voice_onClick(v);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                v.setPressed(false);
                mSpeechRecognizer.stopListening();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }

        return true;
    }

    /*
    Inner class - Tag getter

    This class is responsible for getting data from wikidata using the WikiDataApiWrapper and doing
    asynctask in order to make the request not from the UI thread
     */
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
            for (String string : list) {
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

    /*
    Inner class - SpeechRecognitionListener

    This class implements the RecognitionListener interface and responsible to record the users
    tasks and print it on the screen
     */
    protected class SpeechRecognitionListener implements RecognitionListener {

        @Override
        public void onBeginningOfSpeech()
        {
            //Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
            //Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error)
        {
            //mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            Toast.makeText(getBaseContext(), R.string.homeActivity_tryRecordingAgain, Toast.LENGTH_LONG).show();
            //Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
            //Log.d(TAG, "onReadyForSpeech"); //$NON-NLS-1$
        }

        @Override
        public void onResults(Bundle results)
        {
            //Log.d(TAG, "onResults"); //$NON-NLS-1$
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do
            if (matches.size() > 0)
            {
                ((EditText) findViewById(R.id.taskadd_editText)).setText(matches.get(0));
            }
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
        }
    }
}