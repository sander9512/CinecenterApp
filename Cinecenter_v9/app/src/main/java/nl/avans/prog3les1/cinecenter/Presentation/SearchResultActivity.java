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
import static nl.avans.prog3les1.cinecenter.Presentation.MainActivity.SEARCHQUERY;

public class SearchResultActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        MovieApiConnector.MovieListener {

    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies = new ArrayList<>();
    private DBHandler dBHandler;

    public final static String SEARCHMOVIE = "SEARCHMOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String query = bundle.getString(SEARCHQUERY);

        dBHandler = new DBHandler(getApplicationContext());

        // Inflate UI and set listeners and adapters and ...
        GridView gridViewMovies = (GridView) findViewById(R.id.SearchGridview);
        movieAdapter = new MovieAdapter(getApplicationContext(), getLayoutInflater(), movies);
        gridViewMovies.setAdapter(movieAdapter);

        gridViewMovies.setOnItemClickListener(this);
        getMovieItems("https://api.themoviedb.org/3/search/movie?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&primary_release_year=2017&query=" + query);
    }

    public void getMovieItems(String ApiUrl) {
        MovieApiConnector task = new MovieApiConnector(this);
        String[] urls = new String[]{ApiUrl};
        task.execute(urls);
    }

    @Override
    public void onMovieItemAvailable(Movie movie) {
        movies.add(movie);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Movie movie = movies.get(position);

        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
        intent.putExtra(MOVIE, movie);

        dBHandler.addMovie(movie);

        startActivity(intent);
    }
}
