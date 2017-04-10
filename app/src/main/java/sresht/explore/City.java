package sresht.explore;


import java.util.ArrayList;

class City {
    final String name;
    final String description;
    final int imageResource;
    protected ArrayList<Venue> mVenues;

    City(String name, String description, int imageResource) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        mVenues = new ArrayList<>();
    }
}
