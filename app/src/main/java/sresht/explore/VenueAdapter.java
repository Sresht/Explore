package sresht.explore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return mVenueList.size();
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.card_layout, viewGroup, false);
        return new VenueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Venue venue  = mVenueList.get(position);
        TextView venueNameTextView = (TextView) holder.itemView.findViewById(R.id.card_title);
        venueNameTextView.setText(venue.name);

        venueNameTextView.setTypeface(Typeface.createFromAsset(
                mContext.getAssets(), "fonts/Tangerine_Bold.ttf"));

        ImageView venueImage = (ImageView) holder.itemView.findViewById(R.id.card_thumbnail);
        venueImage.setImageResource(venue.imageResource);
    }

    private class VenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vName;
        ImageView vImage;

        VenueViewHolder(View v) {
            super(v);
            vName  = (TextView) v.findViewById(R.id.card_title);
            vImage = (ImageView) v.findViewById(R.id.card_thumbnail);
//            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, VenueActivity.class);
            context.startActivity(intent);
        }
    }
}
