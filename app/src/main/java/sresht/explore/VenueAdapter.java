package sresht.explore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class VenueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final List<Venue> mVenueList;
    private LayoutInflater mInflater;
    private SharedPreferences mSharedPref;

    VenueAdapter(final Context context, final ArrayList<Venue> venues, final SharedPreferences
            sharedPref) {
        mContext = context;
        mVenueList = venues;
        mSharedPref = sharedPref;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getItemCount() {
        return mVenueList != null ? mVenueList.size() : 0;
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.card_layout, viewGroup, false);
        return new VenueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final VenueViewHolder venueViewHolder = (VenueViewHolder) holder;

        final TextView venueNameTextView = venueViewHolder.vName;
        final Button venueBookmarkButton = venueViewHolder.vBookmark;

        final Venue venue = mVenueList.get(position);

        final boolean isBookmarked = mSharedPref.getBoolean(venue.id, false);

        // set venue name, font, and size
        venueNameTextView.setText(venue.name);
        venueNameTextView.setTextSize(22);
        venueNameTextView.setTypeface(Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Raleway-Medium.ttf"));

        // set venue location
        venueViewHolder.vLocation.setText(venue.location);

        // set venue category
        venueViewHolder.vCategory.setText(venue.category);
        venueViewHolder.vCategory.setVisibility(View.VISIBLE);

        // set venue image asynchronously using Picasso
        Picasso.with(mContext).load(venue.imagePath).placeholder(R.drawable.default_venue).into
                            (venueViewHolder.vImage);

        // update bookmark button to reflect whether the venue has been bookmarked or not
        venueBookmarkButton.setBackgroundResource(isBookmarked ?
                R.drawable.bookmark_active :
                R.drawable.bookmark_inactive);
        venueBookmarkButton.setVisibility(View.VISIBLE);
        venueBookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mSharedPref.edit();
                // flip the value of the old bookmark state and save it in sharedPreferences in a
                // background process
                final boolean newBookmarkState = !isBookmarked;
                editor.putBoolean(venue.id, newBookmarkState);
                editor.apply();
                venueBookmarkButton.setBackgroundResource(newBookmarkState ? R.drawable
                        .bookmark_active : R.drawable.bookmark_inactive);
            }
        });

        venueViewHolder.vVenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(mContext, VenueImageActivity.class);
                detailIntent.putExtra("venueId", venue.id);
                detailIntent.putExtra("venueName", venue.name);
                mContext.startActivity(detailIntent);
            }
        });
    }

    private class VenueViewHolder extends RecyclerView.ViewHolder {
        final TextView vName;
        final TextView vLocation;
        final TextView vCategory;
        final ImageView vImage;
        final Button vBookmark;
        final RelativeLayout vVenue;


        VenueViewHolder(View currentView) {
            super(currentView);
            vName  = (TextView) currentView.findViewById(R.id.card_title);
            vLocation = (TextView) currentView.findViewById(R.id.card_subtitle);
            vCategory = (TextView) currentView.findViewById(R.id.card_category);
            vImage = (ImageView) currentView.findViewById(R.id.card_thumbnail);
            vBookmark = (Button) currentView.findViewById(R.id.bookmark_button);
            vVenue = (RelativeLayout) currentView;
        }
    }
}
