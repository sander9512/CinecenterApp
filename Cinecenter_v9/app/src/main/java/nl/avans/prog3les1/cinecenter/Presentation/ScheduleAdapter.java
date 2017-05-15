package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Schedule;
import nl.avans.prog3les1.cinecenter.R;

import static nl.avans.prog3les1.cinecenter.Presentation.MovieDetailActivity.RESERVATION;

/**
 * Created by marni on 27-3-2017.
 */

public class ScheduleAdapter extends BaseAdapter implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Schedule> schedules;

    private Reservation reservation;


    ScheduleAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Schedule> schedules, Reservation reservation) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.schedules = schedules;
        this.reservation = reservation;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Log.i(TAG, "getView " + position);

        ViewHolder viewHolder;

        if(convertView == null) {

            Log.i(TAG, "Geen view meegekregen. Nieuwe aanmaken.");

            convertView = layoutInflater.inflate(R.layout.listview_schedule_row, null);

            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.time1 = (TextView) convertView.findViewById(R.id.time1);
            viewHolder.time2 = (TextView) convertView.findViewById(R.id.time2);
            viewHolder.time3 = (TextView) convertView.findViewById(R.id.time3);

            viewHolder.time1.setOnClickListener(this);
            viewHolder.time2.setOnClickListener(this);
            viewHolder.time3.setOnClickListener(this);

            convertView.setTag(viewHolder);
        } else {

            Log.i(TAG, "View meegekregen. hergebruiken.");

            viewHolder = (ViewHolder) convertView.getTag();
        }

        Schedule schedule = schedules.get(position);

        viewHolder.date.setText(schedule.getDate());

        viewHolder.time1.setTag(viewHolder.date.getText());
        viewHolder.time2.setTag(viewHolder.date.getText());
        viewHolder.time3.setTag(viewHolder.date.getText());

        viewHolder.time1.setText(schedule.getTime1());
        viewHolder.time2.setText(schedule.getTime2());
        viewHolder.time3.setText(schedule.getTime3());

        return convertView;
    }

    private static class ViewHolder {
        TextView date;
        TextView time1;
        TextView time2;
        TextView time3;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, RateActivity.class);

        String date = (String) v.getTag();
        TextView textView = (TextView) v;

        reservation.setDate(date);
        reservation.setTime(textView.getText().toString());

        intent.putExtra(RESERVATION, reservation);

        context.startActivity(intent);
    }
}
