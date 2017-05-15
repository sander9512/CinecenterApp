package nl.avans.prog3les1.cinecenter.DataAccess;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import nl.avans.prog3les1.cinecenter.Domain.Movie;

import static android.content.ContentValues.TAG;

/**
 * Created by marni on 29-3-2017.
 */

public class SingleMovieApiConnector extends AsyncTask<String, Void, String> {

    // Call back
    private OnMovieAvailable listener = null;

    // Constructor, set listener
    public SingleMovieApiConnector(OnMovieAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responsCode = -1;
        // De URL die we via de .execute() meegeleverd krijgen
        String MovieUrl = params[0];
        // Het resultaat dat we gaan retourneren
        String response = "";

        Log.i(TAG, "doInBackground - " + MovieUrl);
        try {
            // Maak een URL object
            URL url = new URL(MovieUrl);
            // Open een connection op de URL
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                return null;
            }

            // Initialiseer een HTTP connectie
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            // Voer het request uit via de HTTP connectie op de URL
            httpConnection.connect();

            // Kijk of het gelukt is door de response code te checken
            responsCode = httpConnection.getResponseCode();
            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
                // Log.i(TAG, "doInBackground response = " + response);
            } else {
                Log.e(TAG, "Error, invalid response");
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }

        // Hier eindigt deze methode.
        // Het resultaat gaat naar de onPostExecute methode.
        return response;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onPostExecute(String response) {

        Log.i(TAG, "onPostExecute " + response);

        // Check of er een response is
        if (response == null || response == "") {
            Log.e(TAG, "onPostExecute kreeg een lege response!");
            return;
        }

        JSONObject jsonObject;
        try {
            // Top level json object
            jsonObject = new JSONObject(response);

            String movieId;
            String title;
            String date;
            String summary;
            String longDescription;
            String posterImageUrl;
            String backdropImageUrl;
            Float movieRating;

            if (jsonObject.has("release_date")) {
                date = jsonObject.getString("release_date");
            } else {
                date = "";
            }

            if (jsonObject.has("longDescription")) {
                longDescription = jsonObject.getString("longDescription");
            } else {
                longDescription = "";
            }

            if (jsonObject.has("title")) {
                title = jsonObject.getString("title");
            } else {
                title = "";
            }

            if (jsonObject.has("overview")) {
                summary = jsonObject.getString("overview");
            } else {
                summary = "";
            }

            if (!Objects.equals(jsonObject.getString("poster_path"), "null")) {
                posterImageUrl = "https://image.tmdb.org/t/p/w500/" + jsonObject.getString("poster_path");
            } else {
                posterImageUrl = "http://nubisonline.nl/cinecenter/cinecenter_poster.png";
            }

            if (!Objects.equals(jsonObject.getString("poster_path"), "null")) {
                backdropImageUrl = "https://image.tmdb.org/t/p/w500/" + jsonObject.getString("backdrop_path");
            } else {
                backdropImageUrl = "http://nubisonline.nl/cinecenter/cinecenter_back.png";
            }

            if (jsonObject.has("vote_average")) {
                movieRating = Float.parseFloat(jsonObject.getString("vote_average"));
            } else {
                movieRating = 0.0f;
            }

            if (jsonObject.has("id")) {
                movieId = jsonObject.getString("id");
            } else {
                movieId = "";
            }

            // Create new movie object
            Movie m = new Movie();
            m.setMovieId(movieId);
            m.setTitle(title);
            m.setDate(date);
            m.setSummary(summary);
            m.setLongDescription(longDescription);
            m.setPosterImageUrl(posterImageUrl);
            m.setBackdropImageUrl(backdropImageUrl);
            m.setMovieRating(movieRating);

            //
            // call back with new person data
            //
            listener.onMovieAvailable(m);


        } catch (JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    // Call back interface
    public interface OnMovieAvailable {
        void onMovieAvailable(Movie movie);
    }
}
