package com.musicsearch.rajeev.presentation;

import android.os.Bundle;

import com.musicsearch.rajeev.framework.BaseActivity;

/**
 * Activity to show lyrics of a selected song track.
 */
public class LyricsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyrics_activity);
    }
}
