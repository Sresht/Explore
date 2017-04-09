package sresht.explore;

import java.util.ArrayList;

/**
 * Created by sresht on 4/8/17.
 */

class Venue {
    final String name;
    final int imageResource;
    boolean isBookmarked;
    protected ArrayList<VenueImage> venueImages;

    Venue(String name, int image) {
        this.name = name;
        this.imageResource = image;
        this.isBookmarked = false;
    }

    void bookmark() {
        this.isBookmarked = true;
    }
}
