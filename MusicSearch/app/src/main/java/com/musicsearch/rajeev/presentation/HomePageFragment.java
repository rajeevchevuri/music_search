package com.musicsearch.rajeev.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musicsearch.rajeev.framework.BaseFragment;
import com.musicsearch.rajeev.model.Response;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Home screen to search a song track
 */
public class HomePageFragment extends BaseFragment {

    private String SEARCH_URL = "https://itunes.apple.com/search?term=";
    private String searchKeywordText = "";
    private EditText searchKeywordEditText;
    private String jsonResponse = "";
    private Response response;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);
        searchKeywordEditText = (EditText) view.findViewById(R.id.music_search_text);
        searchButton = (Button) view.findViewById(R.id.music_search_button);
        setListeners();
        return view;
    }

    protected void setListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKeywordText = searchKeywordEditText.getText().toString();
                FetchData data = new FetchData();
                String searchKeyword = searchKeywordText.replaceAll("\\s", "");
                // concatenate user input to the url
                data.JSON_URL = SEARCH_URL + searchKeyword;
                data.execute();
            }
        });
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
        //Read response line by line
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + '\n');
        }
        return builder.toString();
    }

    protected Response jsonToResponse(String jsonResponse) {
        // convert Json object to java object
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonResponse, Response.class);
    }

    protected class FetchData extends AsyncTask<Void, Void, Void> {
        protected String JSON_URL = "";
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        private InputStream inputStream;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show progress dialog
            progressDialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading Content");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                jsonResponse = convertInputStreamToString(inputStream);
                response = jsonToResponse(jsonResponse);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (response != null) {
                // go to new screen and display song tracks
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra("SearchObject", response);
                startActivity(intent);
            }
        }
    }

}
