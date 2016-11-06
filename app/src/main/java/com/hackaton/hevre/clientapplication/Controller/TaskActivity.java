package com.hackaton.hevre.clientapplication.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackaton.hevre.clientapplication.Model.Business;
import com.hackaton.hevre.clientapplication.Model.BusinessListAdapter;
import com.hackaton.hevre.clientapplication.Model.BusinessRowItem;
import com.hackaton.hevre.clientapplication.Model.IModelService;
import com.hackaton.hevre.clientapplication.Model.ModelService;
import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;

/**
 * Created by Ron on 30/10/2016.
 */

public class TaskActivity extends AppCompatActivity implements AdapterViewCompat.OnItemClickListener {

    private ListView mListView;
    private TextView mTitle;
    private IModelService mModelService = ModelService.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mModelService.setDelegate(this);
        ArrayList<BusinessRowItem> businesses = (ArrayList<BusinessRowItem>) getIntent().getSerializableExtra("businessesList");
        String taskName = (String) getIntent().getSerializableExtra("taskName");

        ArrayAdapter<BusinessRowItem> adapter = new BusinessListAdapter(this, businesses);
        this.mListView = (ListView) findViewById(R.id.listView);
        this.mListView.setAdapter(adapter);

        Toolbar toolbarTop = (Toolbar) findViewById(R.id.task_toolbar);
        mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(taskName);
    }

    @Override
    public void onItemClick(AdapterViewCompat<?> parent, View view, int position, long id) {

    }
}
