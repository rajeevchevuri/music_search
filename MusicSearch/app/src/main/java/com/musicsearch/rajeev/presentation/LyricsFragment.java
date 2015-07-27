package com.musicsearch.rajeev.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musicsearch.rajeev.framework.BaseFragment;
import com.musicsearch.rajeev.model.Lyrics;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Fragment to show lyrics of a selected song track.
 */
public class LyricsFragment extends BaseFragment {

    private TextView lyrics;
    private String artistName;
    private String songName;
    private String jsonResponse = "";
    private Lyrics songLyrics;
    private Button homeScreen;
    private String url = "http://lyrics.wikia.com/api.php?artist=";
    private TextView fullLyrics;
    private String fullLyricsText;
    private TextView errorText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyrics_fragment, container, false);
        lyrics = (TextView) view.findViewById(R.id.lyrics);
        errorText = (TextView) view.findViewById(R.id.error_text);
        homeScreen = (Button) view.findViewById(R.id.home_screen);
        fullLyrics = (TextView) view.findViewById(R.id.click_here);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        artistName = getActivity().getIntent().getStringExtra("artistName");
        songName = getActivity().getIntent().getStringExtra("song");
        showLyrics();
        setListeners();
    }

    protected void setListeners() {
        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to home screen
                Intent intent = new Intent(LyricsFragment.this.getActivity(), HomePageActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        fullLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open full lyrics in external browser
                if (fullLyricsText != null && !fullLyricsText.isEmpty()) {
                    String lyricsUrl = fullLyricsText.replace("\\", "");
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(lyricsUrl));
                    startActivity(i);
                }
            }
        });
    }

    protected void showLyrics() {
        // Download lyrics in background
        LyricsData data = new LyricsData();
        String artist = artistName.replaceAll("\\s", "+");
        String song = songName.replaceAll("\\s", "+");
        data.JSON_URL = url + artist + "&song=" + song + "&fmt=realjson";
        data.execute();
    }

    protected String convertInputStreamToString(InputStream inputstream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream), 1024);
        try {
            return readLines(reader);
        } finally {
            reader.close();
        }
    }

    protected String readLines(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + '\n');
        }
        return builder.toString();
    }

    protected Lyrics jsonToResponse(String jsonResponse) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonResponse, Lyrics.class);

    }

    protected class LyricsData extends AsyncTask<Void, Void, Void> {

        protected String JSON_URL = "";
        protected InputStream inputStream;

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LyricsFragment.this.getActivity(), "Please Wait", "Loading Content");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                jsonResponse = convertInputStreamToString(inputStream);
                songLyrics = jsonToResponse(jsonResponse);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            String lyricsResponse = songLyrics.getLyrics();
            fullLyricsText = songLyrics.getUrl();
            if (lyricsResponse != null && !lyricsResponse.isEmpty()) {
                errorText.setVisibility(View.GONE);
                lyrics.setText(songLyrics.getLyrics());
                fullLyrics.setVisibility(View.VISIBLE);

            } else {
                fullLyrics.setVisibility(View.GONE);
                errorText.setText(View.VISIBLE);
            }
            progressDialog.dismiss();
        }
    }

}
