package com.rapidapps.instagram.realtimedatabase;

/**
 * Created by User on 1/29/2018.
 */

public class Artist {
    String artistId;
    String artistName;
    String artisGenre;
    public Artist()
    {

    }

    public Artist(String artistId, String artistName, String artisGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artisGenre = artisGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtisGenre() {
        return artisGenre;
    }
}
