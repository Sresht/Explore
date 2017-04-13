package sresht.explore;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class CityAdapter extends BaseAdapter {
    private Context mContext;
    private final ArrayList<City> mCityList;
    private LayoutInflater mInflater;


    CityAdapter(Context context, ArrayList<City> cities) {
        mContext = context;
        mCityList = cities;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mCityList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cardView = mInflater.inflate(R.layout.card_layout, parent, false);
        TextView cityNameTextView = (TextView) cardView.findViewById(R.id.card_title);
        TextView cityDescriptionTextView = (TextView) cardView.findViewById(R.id.card_subtitle);
        ImageView cityImage = (ImageView) cardView.findViewById(
                R.id.card_thumbnail);

//        set view to reflect the City model object
        City city = (City) getItem(position);
        cityNameTextView.setText(city.name);
        cityNameTextView.setTypeface(Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Tangerine_Bold.ttf"));

        cityDescriptionTextView.setText(city.description);
        cityDescriptionTextView.setTypeface(Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Raleway-Medium.ttf"));

        cityImage.setImageResource(city.imageResource);

        return cardView;
    }
}
