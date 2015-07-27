package com.musicsearch.rajeev.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.musicsearch.rajeev.framework.BaseFragment;
import com.musicsearch.rajeev.model.MusicResponse;
import com.musicsearch.rajeev.model.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to show list of song tracks.
 */
public class SearchResultFragment extends BaseFragment {

    private ListView searchResultsList;
    private Response response;
    private TextView errorText;
    private Button tryAgain;
    private List<MusicResponse> musicResponseList = new ArrayList<MusicResponse>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result_fragment, container, false);
        searchResultsList = (ListView) view.findViewById(R.id.search_results_list);
        errorText = (TextView) view.findViewById(R.id.error_text);
        tryAgain = (Button) view.findViewById(R.id.try_again);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showMusicResponse();
        setListeners();
    }

    protected void showMusicResponse() {
        response = (Response) getActivity().getIntent().getSerializableExtra("SearchObject");
        musicResponseList = response.getResults();
        if (!musicResponseList.isEmpty()) {
            hideErrorMessage();
            searchResultsList.setAdapter(new DataListAdapter(SearchResultFragment.this.getActivity(), musicResponseList));
        } else {
            // show error message when song tracks are not available
            showErrorMessage();
        }
        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicResponse response = musicResponseList.get(position);
                // Open lyrics when a song track is selected
                Intent intent = new Intent(SearchResultFragment.this.getActivity(), LyricsActivity.class);
                intent.putExtra("artistName", response.getArtistName());
                intent.putExtra("song", response.getTrackName());
                startActivity(intent);
            }
        });
    }

    protected void setListeners() {
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to home Screen
                Intent i = new Intent(SearchResultFragment.this.getActivity(), HomePageActivity.class);
                startActivity(i);
            }
        });
    }

    protected void hideErrorMessage() {
        errorText.setVisibility(View.GONE);
        tryAgain.setVisibility(View.GONE);
    }

    private void showErrorMessage() {
        errorText.setVisibility(View.VISIBLE);
        tryAgain.setVisibility(View.VISIBLE);
    }

    protected void setTrackImage(ImageView imageView, String url) {
        // Download Image in background
        new DownloadImageTask(imageView).execute(url);
    }

    protected class DataListAdapter extends BaseAdapter {

        private List<MusicResponse> resultsList = new ArrayList<MusicResponse>();
        private Context context;

        public DataListAdapter(Context context, List<MusicResponse> resultsList) {
            this.context = context;
            this.resultsList = resultsList;
        }

        @Override
        public int getCount() {
            return resultsList.size();
        }

        @Override
        public MusicResponse getItem(int position) {
            return resultsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(this.context);
                convertView = layoutInflater.inflate(R.layout.single_row, parent, false);
            }

            ImageView trackImage = (ImageView) convertView.findViewById(R.id.track_image);
            TextView trackTitle = (TextView) convertView.findViewById(R.id.track_name);
            TextView artistName = (TextView) convertView.findViewById(R.id.artist_name);

            MusicResponse response = getItem(position);
            setTrackImage(trackImage, response.getArtworkUrl100());
            trackTitle.setText(response.getTrackName());
            artistName.setText(response.getArtistName());
            return convertView;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView trackImage;

        public DownloadImageTask(ImageView image) {
            this.trackImage = image;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap icon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                icon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return icon;
        }

        protected void onPostExecute(Bitmap result) {
            trackImage.setImageBitmap(result);
        }
    }


}
