package nl.avans.prog3les1.cinecenter.Domain;


/**
 * Created by MSI-PC on 14-3-2017.
 */

import java.io.Serializable;

public class Movie implements Serializable {

    private String id;
    private String title;
    private String date;
    private String summary;
    private String longDescription;
    private String posterImageUrl;
    private String backdropImageUrl;
    private float Rating;

    public Movie(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public void setPosterImageUrl(String posterImageUrl) {
        this.posterImageUrl = posterImageUrl;
    }

    public String getBackdropImageUrl() {
        return backdropImageUrl;
    }

    public void setBackdropImageUrl(String backdropImageUrl) {
        this.backdropImageUrl = backdropImageUrl;
    }

    public float getMovieRating() {
        return Rating;
    }

    public void setMovieRating(float rating) {
        Rating = rating;
    }

    public String getMovieId() {
        return id;
    }

    public void setMovieId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "" + title + " - " + summary;
    }
}
