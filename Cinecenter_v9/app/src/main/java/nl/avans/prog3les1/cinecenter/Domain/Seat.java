package nl.avans.prog3les1.cinecenter.Domain;

/**
 * Created by MSI-PC on 27-3-2017.
 */

public class Seat {
    private int SeatNumber;
    private String seatColor;

    public String getSeatColor() {
        return seatColor;
    }

    public void setSeatColor(String seatColor) {
        this.seatColor = seatColor;
    }

    public int getSeatNumber() {
        return SeatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        SeatNumber = seatNumber;
    }
}
