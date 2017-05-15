package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.BusinessLogic.TicketsTotal;
import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Rate;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;
import nl.avans.prog3les1.cinecenter.R;

/**
 * Created by marni on 2-4-2017.
 */

class OverviewAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<ArrayList<Ticket>> tickets = new ArrayList<>();

    OverviewAdapter(Context context, LayoutInflater layoutInflater, ArrayList<ArrayList<Ticket>> tickets) {

        this.context = context;
        this.layoutInflater = layoutInflater;
        this.tickets = tickets;
    }

    @Override
    public int getCount() {

        int rateCount = 0;

        for (ArrayList<Ticket> specificTickets : tickets) {

            if (specificTickets.size() > 0) {

                rateCount++;
            }
        }
        return rateCount;
    }

    @Override
    public Object getItem(int position) {

        return tickets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i(TAG, "getView " + position);

        final OverviewAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            Log.i(TAG, "Geen view meegekregen. Nieuwe aanmaken.");

            convertView = layoutInflater.inflate(R.layout.listview_overview_row, null);

            viewHolder = new OverviewAdapter.ViewHolder();
            viewHolder.textViewOverviewAmountAndRate = (TextView) convertView.findViewById(R.id.textViewOverviewAmountAndRate);
            viewHolder.textViewOverviewPrice = (TextView) convertView.findViewById(R.id.textViewOverviewPrice);

            convertView.setTag(viewHolder);
        } else {

            Log.i(TAG, "View meegekregen. hergebruiken.");

            viewHolder = (OverviewAdapter.ViewHolder) convertView.getTag();
        }

        int i = 0;

        for (ArrayList<Ticket> specificTickets : tickets) {

            if (specificTickets.size() > 0) {

                if (i == position) {

                    DBHandler dbHandler = new DBHandler(context);

                    String rateId = specificTickets.get(0).getRateId();

                    Rate rate = dbHandler.getRateById(rateId);

                    int amount = specificTickets.size();
                    String amountAndRate = amount + "x " + rate.getRate();

                    DecimalFormat df = new DecimalFormat("0.00##");

                    double d = rate.getPrice()*amount;
                    String price = "€" + df.format(d);
                    price = price.replace(".", ",");

                    viewHolder.textViewOverviewAmountAndRate.setText(amountAndRate);
                    viewHolder.textViewOverviewPrice.setText(price);
                }

                i++;
            }
        }

        return convertView;
    }

    private class ViewHolder {

        private TextView textViewOverviewAmountAndRate;
        private TextView textViewOverviewPrice;
    }
}
