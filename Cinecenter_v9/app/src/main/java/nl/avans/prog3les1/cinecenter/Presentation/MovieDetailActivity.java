package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.io.InputStream;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.DataAccess.MovieApiConnector;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.Presentation.MainActivity.MOVIE;

public class MovieDetailActivity extends AppCompatActivity implements MovieApiConnector.MovieListener {

    public final String TAG = this.getClass().getSimpleName();

    public static final String RESERVATION = "RESERVATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMovieItems("https://api.themoviedb.org/3/movie/now_playing?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US&page=1");

        TextView textTitleMovieDetailView = (TextView) findViewById(R.id.textTitleMovieDetailView);
        TextView textDateMovieDetailView = (TextView) findViewById(R.id.textDateMovieDetailView);
        TextView textDescriptionMovieDetailView = (TextView) findViewById(R.id.textDescriptionMovieDetailView);
        RatingBar ratingMovieDetailView = (RatingBar) findViewById(R.id.ratingMovieDetailView);
        ImageView imageMovieDetailView = (ImageView) findViewById(R.id.imageMovieDetailView);
        Button button = (Button) findViewById(R.id.button_detail);

        Bundle bundle = getIntent().getExtras();

        final Movie m = (Movie) bundle.getSerializable(MOVIE);

        assert m != null;
        textTitleMovieDetailView.setText(m.getTitle());
        new ImageLoader(imageMovieDetailView).execute(m.getBackdropImageUrl());
        textDescriptionMovieDetailView.setText(m.getSummary());

        String date = "Release Date: " + m.getDate();
        textDateMovieDetailView.setText(date);
        ratingMovieDetailView.setRating(m.getMovieRating()/2);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ScheduleActivity.class);

                Reservation reservation = new Reservation();
                reservation.setMovieId(m.getMovieId());

                i.putExtra(RESERVATION, reservation);
                startActivity(i);
            }
        });
    }

    public void getMovieItems(String ApiUrl) {
        MovieApiConnector task = new MovieApiConnector(this);
        String[] urls = new String[] {ApiUrl};
        task.execute(urls);
    }

    @Override
    public void onMovieItemAvailable(Movie movie) {
        String ee = movie.getBackdropImageUrl();
    }

    private class ImageLoader extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        ImageLoader(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
