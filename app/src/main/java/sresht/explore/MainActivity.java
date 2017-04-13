package sresht.explore;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import sresht.explore.databinding.ListViewLayoutBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);

        final Context mContext = this;

        ListViewLayoutBinding binding = DataBindingUtil.setContentView(this,
                R.layout.list_view_layout);
        ListView mListView = binding.citiesList;

        final ArrayList<City> citiesList = getCities();
        CityAdapter adapter = new CityAdapter(this, citiesList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = citiesList.get(position);
                Intent detailIntent = new Intent(mContext, VenueActivity.class);
                detailIntent.putExtra("cityName", city.name);

                startActivity(detailIntent);
            }
        });
    }

    private ArrayList<City> getCities() {
        ArrayList<City> result = new ArrayList<>();
        result.add(new City(
                "Beijing",
                "The Forbidden City contains the largest wall in the entire world. A city rich " +
                        "with history and industry, it is a must see destination for anybody who " +
                        "is visiting China.",
                R.drawable.beijing));
        result.add(new City("London",
                "The city that quickly became the cultural and imperial capital of the world. " +
                        "There " +
                        "are endless activities and sights to see in this most regal city.",
                R.drawable.london));
        result.add(new City("Johannesburg",
                "A bustling metropolis of fashion, architecture, landscape, and food, " +
                        "Johannesburg has something to offer all of its visitors. Bring a car, " +
                        "though, because there's lots to explore!",
                R.drawable.johannesburg));
        result.add(new City("San Francisco",
                "A city that's divided into hundreds of unique neighborhoods, San Francisco is a " +
                        "city of micro-cultures. Walk half a mile in any direction and everything" +
                        " will change - including the weather.",
                R.drawable.san_francisco));
        result.add(new City("Sao Paulo",
                "Brazil's most vibrant financial center comes to life after the sun goes down. " +
                        "Explore the unique architecture during the day and party on the playa " +
                        "until sunrise.",
                R.drawable.sao_paulo));
        return result;
    }
}
