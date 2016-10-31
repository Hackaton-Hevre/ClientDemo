package com.hackaton.hevre.clientapplication.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hackaton.hevre.clientapplication.Model.IModelService;
import com.hackaton.hevre.clientapplication.Model.ModelService;
import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;

/**
 * Created by Ron on 30/10/2016.
 */

public class TaskActivity extends AppCompatActivity implements AdapterViewCompat.OnItemClickListener {

    private ListView mListView;
    private IModelService mModelService = ModelService.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mModelService.setDelegate(this);
        ArrayList<String> businesses = (ArrayList<String>) getIntent().getSerializableExtra("businessesList");
        if (businesses.isEmpty()) {
            businesses = new ArrayList<>();
            businesses.add("Not found");
        }

        System.out.println("+++++++++++++++++" + businesses.get(0));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, businesses);
        this.mListView = (ListView) findViewById(R.id.listView);
        this.mListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterViewCompat<?> parent, View view, int position, long id) {

    }
}
