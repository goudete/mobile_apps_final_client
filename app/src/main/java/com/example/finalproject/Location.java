package com.example.finalproject;

public class Location {
    private int locationId;
    private int userId;
    private String locationName;
    private String googlePlaceId;
    private String description;
    private int rating;

    public Location(int locationId, int userId, String locationName, String googlePlaceId, String description, int rating) {
        this.locationId = locationId;
        this.userId = userId;
        this.locationName = locationName;
        this.googlePlaceId = googlePlaceId;
        this.description = description;
        this.rating = rating;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getGooglePlaceId() {
        return googlePlaceId;
    }

    public void setGooglePlaceId(String googlePlaceId) {
        this.googlePlaceId = googlePlaceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
