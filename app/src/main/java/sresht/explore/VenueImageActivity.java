package sresht.explore;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import sresht.explore.databinding.RecyclerViewLayoutBinding;

public class VenueImageActivity extends AppCompatActivity {
    RecyclerView mImagesView;
    Context mContext;
    String mVenueName;
    private ArrayList<String> mImageURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mVenueName = this.getIntent().getExtras().getString("venueName");
        mImageURLs = new ArrayList<>();

        RecyclerViewLayoutBinding binding = DataBindingUtil.setContentView(this, R.layout.
                recycler_view_layout);
        mImagesView = binding.recyclerView;
        mImagesView.setHasFixedSize(true);

        new GetVenueImagesTask().execute(this.getIntent().getExtras().getString("venueId"));
    }

    private class GetVenueImagesTask extends AsyncTask<String, Void, JSONArray> {
        private Exception exception;

        @Override
        protected JSONArray doInBackground(String... params) {
            String venueId = params[0];
            URL venueURL;
            try {
                // TODO refactor limit into a constant
                venueURL = new URL(String.format(
                        "https://api.foursquare" +
                                ".com/v2/venues/%s/photos?client_id=%s&client_secret=%s&limit=40" +
                                "&v=%s",
                                venueId, Secret.CLIENT_ID, Secret.CLIENT_SECRET, "20170409"));

                HttpURLConnection urlConnection = (HttpURLConnection) venueURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();
                Scanner s = new Scanner(stream).useDelimiter("\\A");

                String response = s.hasNext() ? s.next() : "";
                JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                return jsonObj.getJSONObject("response").getJSONObject("photos")
                        .getJSONArray("items");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray photos) {
            try {
                for (int i = 0; i < photos.length(); i++) {
                    JSONObject photo = (JSONObject) photos.get(i);
                    // TODO move 300x300 into constants file
                    String imageURL = String.format("%s300x300%s", photo.getString("prefix"),
                            photo.getString("suffix"));
                    mImageURLs.add(imageURL);
                }
            } catch (Exception e) {
                // TODO better exception handling
                this.exception = e;
            } finally {
                ImageAdapter adapter = new ImageAdapter(mContext, mImageURLs);
                GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
                mImagesView.setLayoutManager(layoutManager);
                mImagesView.setAdapter(adapter);
            }
        }
    }
}
