package sresht.explore;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class VenueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final List<Venue> mVenueList;
    private LayoutInflater mInflater;

    VenueAdapter(final Context context, final ArrayList<Venue> venues) {
        mContext = context;
        mVenueList = venues;
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
        TextView venueNameTextView = (TextView) holder.itemView.findViewById(R.id.card_title);
        ImageView venueImage = (ImageView) holder.itemView.findViewById(R.id.card_thumbnail);
        Button venueBookmarkButton = (Button) holder.itemView.findViewById(R.id.bookmark_button);

        Venue venue  = mVenueList.get(position);
        venueNameTextView.setText(venue.name);
        venueNameTextView.setTypeface(Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Tangerine_Bold.ttf"));
        venueImage.setImageResource(venue.imageResource);
        venueBookmarkButton.setBackgroundResource(venue.isBookmarked ?
                R.drawable.bookmark_active :
                R.drawable.bookmark_inactive);
    }

    private class VenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vName;
        ImageView vImage;
        Button vBookmark;

        VenueViewHolder(View currentView) {
            super(currentView);
            vName  = (TextView) currentView.findViewById(R.id.card_title);
            vImage = (ImageView) currentView.findViewById(R.id.card_thumbnail);
            vBookmark = (Button) currentView.findViewById(R.id.bookmark_button);
            vBookmark.setVisibility(View.VISIBLE);
            vBookmark.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    vBookmark.setBackgroundResource(R.drawable.bookmark_active);
                    return false;
                }

            });
            currentView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
//            Intent intent = new Intent(context, VenueActivity.class);
//            context.startActivity(intent);
        }
    }
}
