package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Seat;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.R.drawable.ic_event_seat_black_24dp;
import static nl.avans.prog3les1.cinecenter.R.drawable.ic_event_seat_blue_24dp;
import static nl.avans.prog3les1.cinecenter.R.drawable.ic_event_seat_red_24dp;

/**
 * Created by MSI-PC on 27-3-2017.
 */



public class SeatAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList mSeatArrayList;
    private LayoutInflater mInflator;
    private DBHandler seatDBHandler;

    public SeatAdapter(Context c, LayoutInflater layoutInflater, ArrayList<Seat> seatArrayList) {
        mContext = c;
        mSeatArrayList = seatArrayList;
        mInflator = layoutInflater;
        seatDBHandler = new DBHandler(c);

    }

    public int getCount() {
        return mSeatArrayList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;


        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.seat_gridview_row, null);

            convertView.setLayoutParams(new GridView.LayoutParams(85, 85));
            convertView.setPadding(2, 2, 2, 2);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.seat_type);
            viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Seat seat = (Seat) mSeatArrayList.get(position);

        if(seat.getSeatColor().equals("red")){
            viewHolder.imageView.setImageResource(ic_event_seat_red_24dp);
        } else if(seat.getSeatColor().equals("blue")){
            viewHolder.imageView.setImageResource(ic_event_seat_blue_24dp);
        } else {
            viewHolder.imageView.setImageResource(ic_event_seat_black_24dp);
        }

        return convertView;
    }
    private static class ViewHolder {
        public ImageView imageView;
    }

}
