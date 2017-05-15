package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.Domain.Review;
import nl.avans.prog3les1.cinecenter.R;

/**
 * Created by Sander on 4/1/2017.
 */

public class ReviewAdapter extends BaseAdapter {
    private final String TAG = getClass().getSimpleName();

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Review> reviewList;

    public ReviewAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Review> reviewList) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        int size = reviewList.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_review_row, null);

            viewHolder = new ViewHolder();
            viewHolder.reviewAuthor = (TextView) convertView.findViewById(R.id.reviewAuthorID);
            viewHolder.reviewText = (TextView) convertView.findViewById(R.id.reviewTextID);
            viewHolder.reviewRating = (RatingBar) convertView.findViewById(R.id.reviewRatingID);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Review r = (Review) reviewList.get(position);
        viewHolder.reviewAuthor.setText("Anonymous");
        viewHolder.reviewText.setText(r.getReviewText());
        viewHolder.reviewRating.setRating(r.getRating());

        return convertView;
    }


    private static class ViewHolder {
        public RatingBar reviewRating;
        public TextView reviewAuthor, reviewText;

    }
}
