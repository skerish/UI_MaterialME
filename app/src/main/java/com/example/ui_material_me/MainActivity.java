package com.example.ui_material_me;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSportsData = new ArrayList<>();

        mAdapter = new SportsAdapter(this, mSportsData);

        recyclerView.setAdapter(mAdapter);

        // To get the data.
        initializeData();
    }

    /**
     * Initialize the sports data from resources.
     */
    private void initializeData() {
        String[] sports_title = getResources().getStringArray(R.array.sports_title);
        String[] sports_info = getResources().getStringArray(R.array.sports_info);
        TypedArray sports_image = getResources().obtainTypedArray(R.array.sports_images);

        /**
         *  A TypedArray allows you to store an array of other XML resources. Using a TypedArray,
         *  you can obtain the image resources as well as the sports title and information by using
         *  indexing in the same loop.
         *
         *  int[] won't work.
         */

        // Clears the existing data to avoid duplication.
        mSportsData.clear();

        // Creates the ArrayList of Sports object with title and info about each sport.
        for (int i = 0; i < sports_title.length; i++) {
            mSportsData.add(new Sport(sports_title[i], sports_info[i], sports_image.getResourceId(i, 0)));
        }

        sports_image.recycle();              // Required to use with TypedArray.
        mAdapter.notifyDataSetChanged();
    }
}
