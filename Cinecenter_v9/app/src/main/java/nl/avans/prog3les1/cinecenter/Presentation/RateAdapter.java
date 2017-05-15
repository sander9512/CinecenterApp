package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.internal.util.Predicate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import nl.avans.prog3les1.cinecenter.BusinessLogic.TicketsTotal;
import nl.avans.prog3les1.cinecenter.Domain.Rate;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;
import nl.avans.prog3les1.cinecenter.R;

/**
 * Created by marni on 28-3-2017.
 */

class RateAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();

    private TicketsTotal.OnTotalChanged listener;

    private Reservation reservation;

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Rate> rates;

    private ArrayList<ArrayList<Ticket>> tickets = new ArrayList<>();

    private TicketsTotal ticketsTotal;

    RateAdapter(TicketsTotal.OnTotalChanged listener, Context context, LayoutInflater layoutInflater, ArrayList<Rate> rates, Reservation reservation) {

        this.listener = listener;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.rates = rates;

        this.reservation = reservation;

        for (int i = 0; i < rates.size(); i++) {

            tickets.add(new ArrayList<Ticket>());
        }

        this.ticketsTotal = new TicketsTotal(context, tickets);
    }

    @Override
    public int getCount() {
        return rates.size();
    }

    @Override
    public Object getItem(int position) {
        return rates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Log.i(TAG, "getView " + position);

        final ViewHolder viewHolder;

        if (convertView == null) {
            Log.i(TAG, "Geen view meegekregen. Nieuwe aanmaken.");

            convertView = layoutInflater.inflate(R.layout.listview_type_row, null);

            viewHolder = new ViewHolder();
            viewHolder.textViewType = (TextView) convertView.findViewById(R.id.textViewType);
            viewHolder.textViewPrice = (TextView) convertView.findViewById(R.id.textViewPrice);
            viewHolder.spinner = (Spinner) convertView.findViewById(R.id.spinner);

            convertView.setTag(viewHolder);
        } else {

            Log.i(TAG, "View meegekregen. hergebruiken.");

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Rate rate = rates.get(position);

        viewHolder.textViewType.setText(rate.getRate());

        DecimalFormat df = new DecimalFormat("0.00##");
        String price = "€" + df.format(rate.getPrice());
        price = price.replace(".", ",");
        viewHolder.textViewPrice.setText(price);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewHolder.spinner.setAdapter(adapter);
        viewHolder.spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {

                ArrayList<Ticket> specificTickets = new ArrayList<>();

                int spinnerValue = Integer.parseInt(viewHolder.spinner.getSelectedItem().toString());

                Ticket ticket;

                for (int i = 0; i < spinnerValue; i++) {

                    ticket = new Ticket();

                    ticket.setReservationId(reservation.getId());
                    ticket.setRateId(rate.getId());

                    specificTickets.add(ticket);
                }

                tickets.set(position, specificTickets);

                listener.onTotalChanged(ticketsTotal.getPriceTotal(), ticketsTotal.getTotal(), tickets);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return convertView;
    }

    private class ViewHolder {

        private TextView textViewType;
        private TextView textViewPrice;
        private Spinner spinner;
    }
}

