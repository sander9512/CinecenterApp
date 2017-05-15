package nl.avans.prog3les1.cinecenter.Domain;

import java.io.Serializable;

/**
 * Created by marni on 28-3-2017.
 */

public class Ticket implements Serializable {

    private String rateId;
    private int seatId;
    private String reservationId;

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
}