package sresht.explore;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sresht on 4/8/17.
 */

class Venue {
    final String name;
    final String id;
    String imagePath;
    String location;
    String category;
    protected ArrayList<String> imagePaths;

    Venue(String name, String imagePath, String venueId) {
        this(name, venueId);
        this.imagePath = imagePath;
    }

    Venue(String name, String venueId) {
        this.name = name;
        this.id = venueId;
        this.imagePaths = new ArrayList<>();
    }
}
