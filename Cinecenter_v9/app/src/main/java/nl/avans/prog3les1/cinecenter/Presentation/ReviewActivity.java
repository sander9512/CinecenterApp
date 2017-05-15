package nl.avans.prog3les1.cinecenter.Presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Review;
import nl.avans.prog3les1.cinecenter.R;

public class ReviewActivity extends AppCompatActivity {
    private ReviewAdapter reviewAdapter;
    private EditText eTextContent, eTextRating;
    private RatingBar reviewRating;
    ArrayList<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBHandler dbHandler = new DBHandler(getApplicationContext());

        ListView reviewListView = (ListView) findViewById(R.id.reviewListViewID);
        eTextContent = (EditText) findViewById(R.id.reviewEditTextID);
        reviewRating = (RatingBar) findViewById(R.id.ratingBarReview);

        reviews = (ArrayList<Review>) dbHandler.getAllReviews();

        reviewAdapter = new ReviewAdapter(getApplicationContext(),getLayoutInflater(),reviews);
        reviewListView.setAdapter(reviewAdapter);
        reviewAdapter.notifyDataSetChanged();

        eTextContent.clearFocus();
        reviewListView.requestFocus();

        Button submitReview = (Button) findViewById(R.id.reviewButtonID);

        submitReview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Review review = new Review();
                int rating = (int) reviewRating.getRating();
                String content = eTextContent.getText().toString();
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                if (rating != 0 && !content.isEmpty()) {
                    Log.i("Rating", "Rating =" + rating);
                    review.setRating(rating);
                    review.setReviewText(content);
                    Log.i("Content", "Content =" + content);

                    dbHandler.addReview(review);
                    reviews.add(0, review);

                    reviewAdapter.notifyDataSetChanged();

                    eTextContent.setText("");
                    reviewRating.setRating(0);
                }

                else {
                    Toast toast = Toast.makeText(getApplicationContext(),"Review is unfinished",Toast.LENGTH_SHORT);
                    toast.show();
                    reviewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
