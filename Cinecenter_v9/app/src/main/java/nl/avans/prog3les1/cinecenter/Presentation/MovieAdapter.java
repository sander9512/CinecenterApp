package nl.avans.prog3les1.cinecenter.Presentation;

/**
 * Created by MSI-PC on 14-3-2017.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.R;

public class MovieAdapter extends BaseAdapter {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList mmovieArrayList;
    private DBHandler movieDBHandler;

    public MovieAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Movie> movieArrayList) {
        mContext = context;
        mInflator = layoutInflater;
        mmovieArrayList = movieArrayList;
    }

    @Override
    public int getCount() {
        int size = mmovieArrayList.size();
        return size;
    }

    @Override
    public Object getItem(int position) {
        return mmovieArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){

            // Koppel de convertView aan de layout van onze eigen row
            convertView = mInflator.inflate(R.layout.listview_movie_row, null);

            // Maak een ViewHolder en koppel de schermvelden aan de velden uit onze eigen row.
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.productRowImageView);

            // Sla de viewholder op in de convertView
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie movie = (Movie) mmovieArrayList.get(position);
        new ImageLoader(viewHolder.imageView).execute(movie.getPosterImageUrl());

        return convertView;
    }

    private static class ViewHolder {
        public ImageView imageView;
    }

    /**
     * Interne asynchrone class om afbeeldingen te laden.
     */
    private class ImageLoader extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public ImageLoader(ImageView imageView) {
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
