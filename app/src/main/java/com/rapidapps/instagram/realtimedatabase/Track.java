package com.rapidapps.instagram.realtimedatabase;

/**
 * Created by User on 1/30/2018.
 */

public class Track {
    private String trackId;
    private String trackName;
    private int trackRating;
    public Track()
    {

    }

    public Track(String trackId, String trackName, int trackRating) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackRating = trackRating;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getTrackRating() {
        return trackRating;
    }
}
