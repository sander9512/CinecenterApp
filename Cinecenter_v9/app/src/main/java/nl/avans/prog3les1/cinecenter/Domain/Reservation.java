package nl.avans.prog3les1.cinecenter.Domain;

import java.io.Serializable;

/**
 * Created by marni on 28-3-2017.
 */
public class Reservation implements Serializable {

    private String id;
    private String date;
    private String time;
    private String movieId;
    private String molliePaymentStatus;
    private String molliePaymentStatusOriginal;
    private int molliePaymentId;
    private int visitorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMolliePaymentStatus() {
        return molliePaymentStatus;
    }

    public void setMolliePaymentStatus(String molliePaymentStatus) {
        this.molliePaymentStatus = molliePaymentStatus;
    }

    public String getMolliePaymentStatusOriginal() {
        return molliePaymentStatusOriginal;
    }

    public void setMolliePaymentStatusOriginal(String molliePaymentStatusOriginal) {
        this.molliePaymentStatusOriginal = molliePaymentStatusOriginal;
    }

    public int getMolliePaymentId() {
        return molliePaymentId;
    }

    public void setMolliePaymentId(int molliePaymentId) {
        this.molliePaymentId = molliePaymentId;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }
}
