package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import nl.avans.prog3les1.cinecenter.BusinessLogic.TicketsTotal;
import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.DataAccess.SingleMovieApiConnector;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.Presentation.MovieDetailActivity.RESERVATION;
import static nl.avans.prog3les1.cinecenter.Presentation.RateActivity.TICKETS;

public class OverviewActivity extends AppCompatActivity implements
        SingleMovieApiConnector.OnMovieAvailable,
        Button.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    private Reservation reservation;
    private ArrayList<ArrayList<Ticket>> tickets;

    private TicketsTotal tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_overview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        reservation = (Reservation) bundle.get(RESERVATION);
        tickets = (ArrayList<ArrayList<Ticket>>) bundle.get(TICKETS);

        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        TextView textViewTime = (TextView) findViewById(R.id.textViewTime);
        TextView textViewSeats = (TextView) findViewById(R.id.textViewSeats);
        TextView textViewTotalPrice = (TextView) findViewById(R.id.textViewTotalPrice);

        ListView listViewOverview = (ListView) findViewById(R.id.listViewOverview);

        tt = new TicketsTotal(getApplicationContext(), tickets);
        textViewTotalPrice.setText(tt.getPriceTotal());

        assert reservation != null;
        String movieId = reservation.getMovieId();
        textViewDate.setText(reservation.getDate());
        textViewTime.setText(reservation.getTime());

        assert tickets != null;
        OverviewAdapter overViewAdapter = new OverviewAdapter(getApplicationContext(), getLayoutInflater(), tickets);
        listViewOverview.setAdapter(overViewAdapter);
        overViewAdapter.notifyDataSetChanged();

        String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US";
        getMovie(apiUrl);

        Button button = (Button) findViewById(R.id.button_order);
        button.setOnClickListener(this);
    }

    public void getMovie(String apiUrl) {

        SingleMovieApiConnector task = new SingleMovieApiConnector(this);
        String[] urls = new String[]{apiUrl};
        task.execute(urls);
    }

    @Override
    public void onMovieAvailable(Movie movie) {

        TextView textViewMovie = (TextView) findViewById(R.id.textViewMovie);

        textViewMovie.setText(movie.getTitle());
    }

    @Override
    public void onClick(View v) {

        TextView textView = (TextView) v;

        Log.i(TAG, textView.getText().toString() + " clicked");

        DBHandler dbHandler = new DBHandler(getApplicationContext());

        dbHandler.addReservation(reservation);

        reservation = dbHandler.getLastInsertedReservation();

        for (ArrayList<Ticket> specificTickets : tickets) {

            for (Ticket t : specificTickets) {

                t.setReservationId(reservation.getId());
                dbHandler.addTicket(t);
            }
        }

        String dt = new Date().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSSS");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dt = sdf.format(c.getTime());

        Log.i(TAG, "onClick called " + dt);
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);

        String url = "http://nubisonline.nl/cinecenter/pay?amount=" + tt.getPriceTotalDouble() + "&order=" + dt;
        myWebLink.setData(Uri.parse(url));
        startActivity(myWebLink);
    }
}
