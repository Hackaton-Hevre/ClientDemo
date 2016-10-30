package com.hackaton.hevre.clientapplication.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hackaton.hevre.clientapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ron on 30/10/2016.
 */

public class TaskActivity extends AppCompatActivity implements AdapterViewCompat.OnItemClickListener {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ArrayList<String> businesses = (ArrayList<String>) getIntent().getSerializableExtra("businessesList");
        if (businesses.isEmpty()) {
            businesses = new ArrayList<>();
            businesses.add("Not found");
        }

        System.out.println("+++++++++++++++++" + businesses.get(0));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, businesses);
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterViewCompat<?> parent, View view, int position, long id) {

    }
}
