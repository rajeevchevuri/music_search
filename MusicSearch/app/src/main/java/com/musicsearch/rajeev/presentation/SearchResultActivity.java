package com.musicsearch.rajeev.presentation;

import android.os.Bundle;

import com.musicsearch.rajeev.framework.BaseActivity;

/**
 * Fragment to show list of song tracks.
 */
public class SearchResultActivity extends BaseActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
    }

}
