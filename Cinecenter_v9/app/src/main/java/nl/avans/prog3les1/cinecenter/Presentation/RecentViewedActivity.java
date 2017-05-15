package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.DataAccess.MovieApiConnector;
import nl.avans.prog3les1.cinecenter.DataAccess.SingleMovieApiConnector;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.Presentation.MainActivity.MOVIE;

public class RecentViewedActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        SingleMovieApiConnector.OnMovieAvailable {

    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_viewed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBHandler dbHandler = new DBHandler(getApplicationContext());

        movies = (ArrayList<Movie>) dbHandler.getAllMovies();

        GridView gridViewMovies = (GridView) findViewById(R.id.RecentGridview);
        MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), getLayoutInflater(), movies);
        gridViewMovies.setAdapter(movieAdapter);
        gridViewMovies.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Movie movie = movies.get(position);

        String apiUrl = "https://api.themoviedb.org/3/movie/" + movie.getMovieId() + "?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US";
        getMovie(apiUrl);
    }

    @Override
    public void onMovieAvailable(Movie movie) {

        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
        intent.putExtra(MOVIE, movie);

        startActivity(intent);
    }

    public void getMovie(String apiUrl) {

        SingleMovieApiConnector task = new SingleMovieApiConnector(this);
        String[] urls = new String[] {apiUrl};
        task.execute(urls);
    }
}
