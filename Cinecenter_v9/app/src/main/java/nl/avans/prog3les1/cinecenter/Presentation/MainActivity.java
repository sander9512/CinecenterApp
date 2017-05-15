package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.DataAccess.MovieApiConnector;
import nl.avans.prog3les1.cinecenter.R;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener,
        MovieApiConnector.MovieListener {

    public final String TAG = this.getClass().getSimpleName();
    public final static String MOVIE = "MOVIE";
    public final static String SEARCHQUERY ="SEARCHQUERY";
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies = new ArrayList<>();
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        dbHandler = new DBHandler(getApplicationContext());

        // Inflate UI and set listeners and adapters and ...
        GridView gridViewMovies = (GridView) findViewById(R.id.MainGridview);
        movieAdapter = new MovieAdapter(getApplicationContext(), getLayoutInflater(), movies);
        gridViewMovies.setAdapter(movieAdapter);

        gridViewMovies.setOnItemClickListener(this);

        getMovieItems("https://api.themoviedb.org/3/movie/now_playing?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&page=1");
    }
    public void getMovieItems(String ApiUrl) {
        MovieApiConnector task = new MovieApiConnector(this);
        String[] urls = new String[] {ApiUrl};
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

        dbHandler.addMovie(movie);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(getApplicationContext(),SearchResultActivity.class);
                i.putExtra(SEARCHQUERY,query);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //text changed, do stuff
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //acties voor filter en search hier afhandelen
        if (id == R.id.genre_id_0) {
            movies.clear();
            getMovieItems("https://api.themoviedb.org/3/movie/now_playing?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&page=1");
        }
        if (id == R.id.genre_id_28) {
            movies.clear();
            getMovieItems("https://api.themoviedb.org/3/discover/movie?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&sort_by=popularity.desc&include_adult=false&with_genres=28&primary_release_date.lte=2017-04-05&primary_release_date.gte=2017-03-10&page=1");
        }
        if (id == R.id.genre_id_12) {
            movies.clear();
            getMovieItems("https://api.themoviedb.org/3/discover/movie?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&sort_by=popularity.desc&include_adult=false&with_genres=12&primary_release_date.lte=2017-04-05&primary_release_date.gte=2017-03-10&page=1");
        }
        if (id == R.id.genre_id_35) {
            movies.clear();
            getMovieItems("https://api.themoviedb.org/3/discover/movie?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&sort_by=popularity.desc&include_adult=false&with_genres=35&primary_release_date.lte=2017-04-05&primary_release_date.gte=2017-03-10&page=1");
        }
        if (id == R.id.genre_id_27) {
            movies.clear();
            getMovieItems("https://api.themoviedb.org/3/discover/movie?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&sort_by=popularity.desc&include_adult=false&with_genres=27&primary_release_date.lte=2017-04-05&primary_release_date.gte=2017-03-10&page=1");
        }
        if (id == R.id.genre_id_37) {
            movies.clear();
            getMovieItems("https://api.themoviedb.org/3/discover/movie?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&sort_by=popularity.desc&include_adult=false&with_genres=37&primary_release_date.lte=2017-04-05&primary_release_date.gte=2017-03-10&page=1");
        }
        if (id == R.id.action_search) {
            Log.i("SEARCH", "Search clicked");
            //acties staan in onCreateOptionsMenu
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //Hier de activities voor het menu opstarten
        if (id == R.id.nav_recent_viewed) {
            Intent i = new Intent(getApplicationContext(), RecentViewedActivity.class);
            startActivity(i);
            Log.i("RECENT_VIEWED", "Recent viewed clicked");
        } else if (id == R.id.nav_general_info) {
            Intent i = new Intent(getApplicationContext(), GeneralInfoActivity.class);
            startActivity(i);
            Log.i("GEN_INFO", "Gen info clicked");
        } else if (id == R.id.nav_tickets) {
            Intent i = new Intent(getApplicationContext(), ReservationActivity.class);
            startActivity(i);
            Log.i("TICKETS", "Ticket overview clicked");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
