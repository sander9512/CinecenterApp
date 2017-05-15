package nl.avans.prog3les1.cinecenter.Domain;

import java.io.Serializable;

/**
 * Created by marni on 27-3-2017.
 */

public class Schedule implements Serializable {

    private String date;
    private String time1, time2, time3;

    public Schedule(String date, String time1, String time2, String time3) {
        this.date = date;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String day) {
        this.date = date;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }
}