package sresht.explore;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import sresht.explore.databinding.ActivityVenueBinding;

/**
 * Created by sresht on 4/9/17.
 */

public class VenueActivity extends AppCompatActivity {
    ArrayList<Venue> mVenuesList;
    RecyclerView venuesView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mVenuesList = new ArrayList<>();
        ActivityVenueBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_venue);
        venuesView = binding.venuesRecyclerView;
        venuesView.setHasFixedSize(true);

        String cityName = this.getIntent().getExtras().getString("cityName");
        populateVenues(cityName);
    }

    private void populateVenues(String cityName) {
//        populates the static venuesList global
        new GetVenuesTask().execute(cityName);
    }

    /**
     * class that provides asynchronous network calling capability for the
     */
    private class GetVenuesTask extends AsyncTask<String, Void, JSONArray> {
        private Exception exception;
        @Override
        protected JSONArray doInBackground(String... params) {
            try {
                String cityName = params[0];
                URL foursquareURL = new URL(String.format(
                        "https://api.foursquare" +
                                ".com/v2/venues/explore?client_id=%s&client_secret=%s&near=%s" +
                                "&limit=25&venuePhotos=1&v=%s", Secret.CLIENT_ID, Secret
                                .CLIENT_SECRET,
                        cityName, "20170409"));
                HttpURLConnection urlConnection = (HttpURLConnection) foursquareURL
                        .openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();
                Scanner s = new Scanner(stream).useDelimiter("\\A");
                String response = s.hasNext() ? s.next() : "";
                try {
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                    return jsonObj.getJSONObject("response").getJSONArray("groups");
                } catch (JSONException e) {
                    // TODO better exception handling
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e) {
                // TODO better exception handling
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray groups) {
            try {
                for (int i = 0; i < groups.length(); i++) {
                    JSONObject group = (JSONObject) groups.get(i);
                    JSONArray items = group.getJSONArray("items");

                    for (int j = 0; j < items.length(); j++) {
                        JSONObject item = (JSONObject) ((JSONObject) items.get(j)).get("venue");
                        Venue venue = new Venue(item.getString("name"), R.drawable.orpheum, item
                                .getString("id"));
                        mVenuesList.add(venue);
                    }
                }

                VenueAdapter adapter = new VenueAdapter(getApplicationContext(), mVenuesList);

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                venuesView.setLayoutManager(layoutManager);
                venuesView.setAdapter(adapter);
            } catch (Exception e) {
                // TODO better exception handling
                this.exception = e;
            }
        }
    }
}
