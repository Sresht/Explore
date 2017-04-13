package sresht.explore;

class Venue {
    final String name;
    final String id;
    String venueCoverImageURL;
    String location;
    String category;

    Venue(String name, String venueId) {
        this.name = name;
        this.id = venueId;
    }
}
