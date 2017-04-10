package sresht.explore;

import java.util.ArrayList;

/**
 * Created by sresht on 4/8/17.
 */

class Venue {
    final String name;
    final String venueId;
    int imageResource;
    boolean isBookmarked;
    protected ArrayList<VenueImage> venueImages;

    Venue(String name, int image, String venueId) {
        this(name, venueId);
        this.imageResource = image;
    }

    Venue(String name, String venueId) {
        this.name = name;
        this.isBookmarked = false;
        this.venueId = venueId;
    }

    void bookmark() {
        this.isBookmarked = true;
    }
}
