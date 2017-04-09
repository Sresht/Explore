package sresht.explore;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sresht.explore.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context mContext = this;

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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
                "Cool place 1! I'm really impressed by how cool this place is! Wow! " +
                        "China is amazing. I can't wait to go back to China because China is just " +
                        "so cool",
                R.drawable.beijing));
        result.add(new City("London",
                "Cool place 2! I'm really impressed by how cool this place is!",
                R.drawable.beijing));
        result.add(new City("Nairobi",
                "Cool place 3! I'm really impressed by how cool this place is!",
                R.drawable.beijing));
        result.add(new City("San Francisco",
                "Cool place 4! I'm really impressed by how cool this place is!",
                R.drawable.beijing));
        result.add(new City("Sao Paulo",
                "Cool place 5! I'm really impressed by how cool this place is!",
                R.drawable.beijing));

        return result;
    }
}
