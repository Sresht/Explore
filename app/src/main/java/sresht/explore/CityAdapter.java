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
        CityViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CityViewHolder();
            convertView = mInflater.inflate(R.layout.card_layout, parent, false);
            viewHolder.vCityName = (TextView) convertView.findViewById(R.id.card_title);
            viewHolder.vCityDescription = (TextView) convertView.findViewById(R.id.card_subtitle);
            viewHolder.vCityImage = (ImageView) convertView.findViewById(
                    R.id.card_thumbnail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CityViewHolder) convertView.getTag();
        }
        // set view to reflect the City model object
        City city = (City) getItem(position);
        viewHolder.vCityName.setText(city.name);
        viewHolder.vCityName.setTypeface(Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Tangerine_Bold.ttf"));

        viewHolder.vCityDescription.setText(city.description);
        viewHolder.vCityDescription.setTypeface(Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Raleway-Medium.ttf"));

        viewHolder.vCityImage.setImageResource(city.imageResource);
        return convertView;
    }

    private class CityViewHolder {
        private TextView vCityName;
        private TextView vCityDescription;
        private ImageView vCityImage;
    }
}
