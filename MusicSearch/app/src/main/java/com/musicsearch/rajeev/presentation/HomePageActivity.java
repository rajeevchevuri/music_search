package com.musicsearch.rajeev.presentation;

import android.os.Bundle;
import android.view.Menu;

/**
 * Home screen to search a song track
 */
import com.musicsearch.rajeev.framework.BaseActivity;


public class HomePageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }
}
