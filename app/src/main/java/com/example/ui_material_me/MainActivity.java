package com.example.ui_material_me;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

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

        // The Android SDK includes a class called ItemTouchHelper that is used to define what
        // happens to RecyclerView list items when the user performs various touch actions, such
        // as swipe, or drag and drop.

        // First argument is for "Dragging" and second one is for "Swiping".
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder initial,
                                  @NonNull RecyclerView.ViewHolder target) {
                int from = initial.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mSportsData.remove(viewHolder.getAdapterPosition());           // getAdapterPosition() for smooth animation.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

        });

        helper.attachToRecyclerView(recyclerView);

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
//
//        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
        mAdapter.notifyDataSetChanged();
    }
}
