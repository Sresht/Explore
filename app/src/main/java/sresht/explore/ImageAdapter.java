package sresht.explore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final ArrayList<String> mImageURLs;
    private final LayoutInflater mInflater;

    ImageAdapter(Context context, ArrayList<String> imageURLs) {
        this.mImageURLs = imageURLs;
        this.mContext = context;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.venue_image_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ImageAdapter.ImageViewHolder venueViewHolder = (ImageViewHolder) holder;

        Picasso.with(mContext).load(mImageURLs.get(position)).placeholder(R.drawable.default_venue)
                .into(venueViewHolder.vImage);
    }

    @Override
    public int getItemCount() {
        return mImageURLs.size();
    }


    private class ImageViewHolder extends RecyclerView.ViewHolder {
        final ImageView vImage;

        ImageViewHolder(View currentView) {
            super(currentView);
            vImage = (ImageView) currentView.findViewById(R.id.venue_image);
        }
    }
}
