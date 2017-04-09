package sresht.explore;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

class CityAdapter extends BaseAdapter {
    private final ArrayList<City> mCityList;
    private Context mContext;
    private LayoutInflater mInflater;


    CityAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCityList = getCities();
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

        City city = (City) getItem(position);
        cityNameTextView.setText(city.name);
        Typeface cityNameTypeFace = Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Tangerine_Bold.ttf");
        cityNameTextView.setTypeface(cityNameTypeFace);

        cityDescriptionTextView.setText(city.description);
        Typeface cityDescriptionTypeFace = Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Raleway-Medium.ttf");
        cityDescriptionTextView.setTypeface(cityDescriptionTypeFace);

        cityImage.setImageResource(city.imageResource);

//        Picasso.with(mContext).load(city.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);

        return cardView;
    }

//    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView vName;
//        ImageView vImage;
//
//        CityViewHolder(View v) {
//            super(v);
//            vName = (TextView) v.findViewById(R.id.card_name);
//            vImage = (ImageView) v.findViewById(R.id.card_image);
//            v.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            Context context = v.getContext();
//            Intent intent = new Intent(context, VenueActivity.class);
//            intent.setAction("android.intent.action.PICK");
//            intent.addCategory("android.intent.category.DEFAULT");
//            context.startActivity(intent);
//        }
//    }
}
