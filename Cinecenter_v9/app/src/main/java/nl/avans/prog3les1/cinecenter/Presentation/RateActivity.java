package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.BusinessLogic.TicketsTotal;
import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.DataAccess.SingleMovieApiConnector;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.Domain.Rate;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.Presentation.MovieDetailActivity.RESERVATION;

public class RateActivity extends AppCompatActivity implements
        TicketsTotal.OnTotalChanged,
        Button.OnClickListener,
        SingleMovieApiConnector.OnMovieAvailable {

    private Reservation reservation;
    private ArrayList<ArrayList<Ticket>> tickets;

    private TextView textViewPriceTotal;
    private TextView textViewAmountTotal;

    public static final String TICKETS = "tickets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get rates from db
        DBHandler dbHandler = new DBHandler(getApplicationContext());

        ArrayList<Rate> rates = (ArrayList<Rate>) dbHandler.getAllRates();
        // end

        Bundle bundle = getIntent().getExtras();

        reservation = (Reservation) bundle.get(RESERVATION);

        assert reservation != null;
        String movieId = reservation.getMovieId();

        String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US";
        getMovie(apiUrl);

        textViewPriceTotal = (TextView) findViewById(R.id.textViewPriceTotal);
        textViewAmountTotal = (TextView) findViewById(R.id.textViewAmountTotal);

        ListView listViewRates = (ListView) findViewById(R.id.listViewOverview);

        RateAdapter rateAdapter = new RateAdapter(this, getApplicationContext(), getLayoutInflater(), rates, reservation);

        listViewRates.setAdapter(rateAdapter);

        rateAdapter.notifyDataSetChanged();

        // button
        Button button = (Button) findViewById(R.id.activityRateButton);

        button.setOnClickListener(this);
    }

    @Override
    public void onTotalChanged(String priceTotal, String total, ArrayList<ArrayList<Ticket>> tickets) {

        textViewPriceTotal.setText(priceTotal);
        textViewAmountTotal.setText(total);

        this.tickets = tickets;
    }

    @Override
    public void onClick(View v) {

        int ticketsCount = 0;

        for (ArrayList<Ticket> specificTickets : tickets) {

            for (Ticket t : specificTickets) {

                ticketsCount++;
            }
        }

        if (ticketsCount > 0) {


            DBHandler dbHandler = new DBHandler(getApplicationContext());
            int movieId = Integer.parseInt(reservation.getMovieId());
            String date = reservation.getDate();
            String time = reservation.getTime();

            int ticketsAvailable = 30 - dbHandler.getAmountOfTickets(movieId, date, time);

            if (ticketsCount <= ticketsAvailable) {

                Start();
            } else {

                Toast.makeText(getApplicationContext(), "Too many tickets selected. " + ticketsAvailable + " tickets left available.", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(getApplicationContext(), "Select at least one ticket", Toast.LENGTH_SHORT).show();
        }
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

    public void Start() {

        Intent intent = new Intent(getApplicationContext(), SeatActivity.class);

        intent.putExtra(RESERVATION, reservation);
        intent.putExtra(TICKETS, tickets);

        startActivity(intent);
    }
}
