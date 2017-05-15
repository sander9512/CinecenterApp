package nl.avans.prog3les1.cinecenter.BusinessLogic;


import android.content.Context;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.Domain.Rate;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;

/**
 * Created by marni on 28-3-2017.
 */

public class TicketsTotal {

    private DBHandler dbHandler;
    private ArrayList<ArrayList<Ticket>> tickets;

    public TicketsTotal(Context context, ArrayList<ArrayList<Ticket>> tickets) {

        this.tickets = tickets;

        dbHandler = new DBHandler(context);
    }

    public String getPriceTotal() {

        double d = 0;

        for (ArrayList<Ticket> specificTickets : tickets) {

            for (Ticket ticket : specificTickets) {

                Rate rate;

                rate = dbHandler.getRateById(ticket.getRateId());
                d += rate.getPrice();
            }
        }

        DecimalFormat df = new DecimalFormat("0.00##");

        return "€" + df.format(d);
    }

    public String getTotal() {

        int total = 0;

        for (ArrayList<Ticket> specificTickets : tickets) {

            for (Ticket ticket : specificTickets) {

                total += 1;
            }
        }

        return total + " tickets";
    }

    public double getPriceTotalDouble() {

        double d = 0;

        for (ArrayList<Ticket> specificTickets : tickets) {

            for (Ticket ticket : specificTickets) {

                Rate rate;

                rate = dbHandler.getRateById(ticket.getRateId());
                d += rate.getPrice();
            }
        }

        return d;
    }

    public interface OnTotalChanged {

        void onTotalChanged(String priceTotal, String total, ArrayList<ArrayList<Ticket>> tickets);
    }
}