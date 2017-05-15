package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.BusinessLogic.SeatManager;
import nl.avans.prog3les1.cinecenter.DataAccess.SingleMovieApiConnector;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Seat;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.Presentation.MovieDetailActivity.RESERVATION;
import static nl.avans.prog3les1.cinecenter.Presentation.RateActivity.TICKETS;

public class SeatActivity extends AppCompatActivity implements
        SeatManager.OnSeatAvailable,
        SingleMovieApiConnector.OnMovieAvailable,
        Button.OnClickListener {

    private Reservation reservation;
    private ArrayList<ArrayList<Ticket>> tickets;

    private ArrayList<Seat> seats = new ArrayList<Seat>();
    private SeatAdapter seatAdapter = null;
    private Seat seat;

    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        reservation = (Reservation) bundle.get(RESERVATION);
        tickets = (ArrayList<ArrayList<Ticket>>) bundle.get(TICKETS);

        assert reservation != null;
        String movieId = reservation.getMovieId();

        String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US";
        getMovie(apiUrl);

        GridView gridview = (GridView) findViewById(R.id.SeatGridview);

        seatAdapter = new SeatAdapter(getApplicationContext(), getLayoutInflater(), seats);
        gridview.setAdapter(seatAdapter);

        SeatManager task = new SeatManager(this, getApplicationContext());

        int ticketsCount = 0;
        assert tickets != null;
        for (ArrayList<Ticket> specificTickets : tickets) {

            for (Ticket t : specificTickets) {

                ticketsCount += 1;
            }
        }
        task.AddSeat(reservation, ticketsCount);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Toast.makeText(getApplicationContext(), "The order of these seats cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        button = (Button) findViewById(R.id.button_order);

        button.setOnClickListener(this);
    }

    @Override
    public void onSeatAvailable(Seat seat) {
        seats.add(seat);
        seatAdapter.notifyDataSetChanged();
    }

    public void getMovie(String apiUrl) {

        SingleMovieApiConnector task = new SingleMovieApiConnector(this);
        String[] urls = new String[]{apiUrl};
        task.execute(urls);
    }

    @Override
    public void onMovieAvailable(Movie movie) {

        TextView textViewComplete = (TextView) findViewById(R.id.textViewComplete);

        assert reservation != null;
        String complete = movie.getTitle() + "\n" + reservation.getDate() + ", " + reservation.getTime();

        textViewComplete.setText(complete);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getApplicationContext(), OverviewActivity.class);

        intent.putExtra(RESERVATION, reservation);
        intent.putExtra(TICKETS, tickets);

        startActivity(intent);
    }
}
