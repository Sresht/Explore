package sresht.explore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by sresht on 4/9/17.
 */

public class VenueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_venue);
//        ActivityVenueBinding binding =
//                DataBindingUtil.setContentView(this, sresht.cityvenues.R.layout.activity_venue);
        RecyclerView venuesView = (RecyclerView) findViewById(R.id.venues_recycler_view);
        venuesView.setHasFixedSize(true);

        String cityName = this.getIntent().getExtras().getString("cityName");
        final ArrayList<Venue> venuesList = getVenues(cityName);
        VenueAdapter adapter = new VenueAdapter(getApplicationContext(), venuesList);
        venuesView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        venuesView.setLayoutManager(layoutManager);
        venuesView.setAdapter(new VenueAdapter(this, venuesList));
    }

    private ArrayList<Venue> getVenues(String cityName) {
//        TODO replace with foursquare API
        ArrayList<Venue> result = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            result.add(new Venue("Orpheum", R.drawable.orpheum));
            result.add(new Venue("Curian", R.drawable.orpheum));
            result.add(new Venue("Slim's", R.drawable.orpheum));
            result.add(new Venue("Bentley", R.drawable.orpheum));
        }

        return result;
    }
}
