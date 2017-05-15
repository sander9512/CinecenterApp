package nl.avans.prog3les1.cinecenter.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.R;

public class ReservationActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBHandler dbHandler = new DBHandler(getApplicationContext());

        ArrayList<Reservation> reservations = (ArrayList<Reservation>) dbHandler.getAllReservations();

        reservationAdapter = new ReservationAdapter(getApplicationContext(), getLayoutInflater(), reservations);

        ListView listView = (ListView) findViewById(R.id.listViewPayment);

        listView.setAdapter(reservationAdapter);

        reservationAdapter.notifyDataSetChanged();
    }
}
