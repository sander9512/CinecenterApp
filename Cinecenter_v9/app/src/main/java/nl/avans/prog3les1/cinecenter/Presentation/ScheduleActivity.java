package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.DataAccess.SingleMovieApiConnector;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Schedule;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.Presentation.MainActivity.MOVIE;
import static nl.avans.prog3les1.cinecenter.Presentation.MovieDetailActivity.RESERVATION;

public class ScheduleActivity extends AppCompatActivity
        implements SingleMovieApiConnector.OnMovieAvailable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Schedule> schedules = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        Reservation reservation = (Reservation) bundle.get(RESERVATION);

        assert reservation != null;
        String movieId = reservation.getMovieId();

        String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US";
        getMovie(apiUrl);

        String dt = new Date().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 7; i++) {

            dt = sdf.format(c.getTime());

            Schedule schedule = new Schedule(dt, "18:30", "20:30", "22:30");
            schedules.add(schedule);

            c.add(Calendar.DATE, 1);
        }

        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getApplicationContext(), getLayoutInflater(), schedules, reservation);

        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(scheduleAdapter);

        scheduleAdapter.notifyDataSetChanged();
    }

    public void getMovie(String apiUrl) {

        SingleMovieApiConnector task = new SingleMovieApiConnector(this);
        String[] urls = new String[]{apiUrl};
        task.execute(urls);
    }

    @Override
    public void onMovieAvailable(Movie movie) {

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setText(movie.getTitle());
    }
}

