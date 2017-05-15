package nl.avans.prog3les1.cinecenter.Domain;

/**
 * Created by Sander on 4/1/2017.
 */

public class Review {
    private String reviewText, reviewAuthor;
    private int rating;


    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewText='" + reviewText +
                ", reviewAuthor='" + reviewAuthor +
                ", rating=" + rating +
                '}';
    }
}