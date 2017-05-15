package nl.avans.prog3les1.cinecenter.BusinessLogic;

import android.content.Context;


import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Seat;
import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;

/**
 * Created by MSI-PC on 31-3-2017.
 */

public class SeatManager {
    private OnSeatAvailable listener = null;
    private DBHandler seatDBHandler;
    private Context c;

    public SeatManager(OnSeatAvailable listener, Context context) {
        this.listener = listener;
        c = context;
        seatDBHandler = new DBHandler(c);
    }

    public void AddSeat(Reservation reservation, int amountOfCurrentSeats) {

        int movieId = Integer.parseInt(reservation.getMovieId());
        String date = reservation.getDate();
        String time = reservation.getTime();

        int amountOfBookedSeats = seatDBHandler.getAmountOfTickets(movieId, date, time);
        for (int i = seatDBHandler.countSeats(); i >= 1; i--) {

            Seat seat = new Seat();

            if (i <= amountOfBookedSeats) {
                seat.setSeatColor("red");
            } else if (i <= (amountOfCurrentSeats + amountOfBookedSeats)) {
                seat.setSeatColor("blue");
            } else {
                seat.setSeatColor("black");
            }

            listener.onSeatAvailable(seat);
        }
    }

    public interface OnSeatAvailable {
        void onSeatAvailable(Seat seat);
    }
}
