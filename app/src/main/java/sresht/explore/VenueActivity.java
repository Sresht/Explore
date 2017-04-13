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

import sresht.explore.databinding.RecyclerViewLayoutBinding;

public class VenueActivity extends AppCompatActivity {
    ArrayList<Venue> mVenuesList;
    RecyclerView mVenuesView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mVenuesList = new ArrayList<>();

        RecyclerViewLayoutBinding binding =
                DataBindingUtil.setContentView(this, R.layout.recycler_view_layout);
        mVenuesView = binding.recyclerView;
        mVenuesView.setHasFixedSize(true);

        String cityName = this.getIntent().getExtras().getString("cityName");
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
                        "https://api.foursquare.com/v2/venues/explore?client_id=%s&client_secret=" +
                                "%s&near=%s&limit=%s&venuePhotos=1&v=%s",
                        Secret.CLIENT_ID,
                        Secret.CLIENT_SECRET,
                        cityName,
                        Constant.VENUE_LIMIT,
                        "20170409"));

                HttpURLConnection urlConnection = (HttpURLConnection) foursquareURL
                        .openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();
                Scanner s = new Scanner(stream).useDelimiter("\\A");

                String response = s.hasNext() ? s.next() : "";
                JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                return jsonObj.getJSONObject("response").getJSONArray("groups");
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
                        Venue venue = new Venue(item.getString("name"), item
                                .getString("id"));
                        venue.venueCoverImageURL = getImagePathFromVenueObject(item);
                        venue.location = getLocationFromVenueObject(item);
                        venue.category = getCategoryFromVenueObject(item);
                        mVenuesList.add(venue);
                    }
                }
            } catch (Exception e) {
                // TODO better exception handling
                this.exception = e;
            } finally {
                VenueAdapter adapter = new VenueAdapter(getApplicationContext(), mVenuesList,
                        getPreferences(Context.MODE_PRIVATE));

                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mVenuesView.setLayoutManager(layoutManager);
                mVenuesView.setAdapter(adapter);
            }
        }

        private String getImagePathFromVenueObject(JSONObject item) throws JSONException {
            // horrible hack to get the photo URL out of the JSON response for a venue from the
            // Foursquare API. There might be (and probably is) a better way to do this.
            JSONObject photo = (JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) (
                    (JSONObject) item.get("photos")).get("groups")).get(0)).get("items")).get(0);
            return String.format("%s%s%s",
                    photo.getString("prefix"),
                    Constant.IMAGE_DIMENSION,
                    photo.getString("suffix"));
        }

        private String getLocationFromVenueObject(JSONObject item) throws JSONException {
            return (String) ((JSONObject) item.get("location")).get("address");
        }

        private String getCategoryFromVenueObject(JSONObject item) throws JSONException {
            return (String) ((JSONObject) ((JSONArray) item.get("categories")).get(0)).get
                    ("shortName");
        }
    }
}
