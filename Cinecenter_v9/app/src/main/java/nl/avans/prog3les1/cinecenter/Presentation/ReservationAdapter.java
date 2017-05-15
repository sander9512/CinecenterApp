package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import nl.avans.prog3les1.cinecenter.DataAccess.DBHandler;
import nl.avans.prog3les1.cinecenter.DataAccess.SingleMovieApiConnector;
import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.Domain.Rate;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;
import nl.avans.prog3les1.cinecenter.R;

/**
 * Created by marni on 3-4-2017.
 */

class ReservationAdapter extends BaseAdapter {

    private final String TAG = getClass().getSimpleName();

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Reservation> reservations;

    public ReservationAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Reservation> reservations) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.reservations = reservations;
    }

    @Override
    public int getCount() {
        return reservations.size();
    }

    @Override
    public Object getItem(int position) {
        return reservations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i(TAG, "getView " + position);

        ViewHolder viewHolder;

        if (convertView == null) {
            Log.i(TAG, "Geen view meegekregen. Nieuwe aanmaken.");

            convertView = layoutInflater.inflate(R.layout.listview_reservation_row, null);

            viewHolder = new ViewHolder();
            viewHolder.textViewPaymentRowTitle = (TextView) convertView.findViewById(R.id.textViewPaymentRowTitle);
            viewHolder.textViewPaymentRowDate = (TextView) convertView.findViewById(R.id.textViewPaymentRowDate);
            viewHolder.textViewPaymentRowTime = (TextView) convertView.findViewById(R.id.textViewPaymentRowTime);
            viewHolder.imageViewPaymentRowQR = (ImageView) convertView.findViewById(R.id.imageViewPaymentRowQR);
            viewHolder.textViewPaymentRowRates = (TextView) convertView.findViewById(R.id.textViewPaymentRowRates);

            convertView.setTag(viewHolder);
        } else {

            Log.i(TAG, "View meegekregen. hergebruiken.");

            viewHolder = (ViewHolder) convertView.getTag();
        }

        Reservation reservation = reservations.get(position);

        String apiUrl = "https://api.themoviedb.org/3/movie/" + reservation.getMovieId() + "?api_key=ff1a482dd1f194534828f6671d28d05c&language=en-US";
        viewHolder.getMovie(apiUrl);

        viewHolder.textViewPaymentRowDate.setText(reservation.getDate());
        viewHolder.textViewPaymentRowTime.setText(reservation.getTime());

        QRCodeWriter writer = new QRCodeWriter();

        try {
            BitMatrix bitMatrix = writer.encode("onze coole Bioscoop app " + reservation.getId(), BarcodeFormat.QR_CODE, 250, 250);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            viewHolder.imageViewPaymentRowQR.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        String s = "";

        DBHandler dbHandler = new DBHandler(context);
        ArrayList<Ticket> tickets = dbHandler.getTicketsByReservationId(reservation.getId());

        ArrayList<Rate> rates = dbHandler.getAllRates();

        for (Rate rate : rates) {
            for (Ticket ticket : tickets) {

                Log.i(TAG, rate.getId() + " " + ticket.getRateId());

                if (Objects.equals(ticket.getRateId(), rate.getId())) {

                    int count = 0;

                    for (Ticket t : tickets) {
                        if (Objects.equals(t.getRateId(), rate.getId())) {
                            count++;
                        }
                    }

                    s += count + "x " + rate.getRate() + "\n";
                    break;
                }
            }
        }

        reservation.getId();
        viewHolder.textViewPaymentRowRates.setText(s);

        return convertView;
    }

    private class ViewHolder implements
            SingleMovieApiConnector.OnMovieAvailable {

        private TextView textViewPaymentRowTitle;
        private TextView textViewPaymentRowDate;
        private TextView textViewPaymentRowTime;
        private ImageView imageViewPaymentRowQR;
        private TextView textViewPaymentRowRates;

        @Override
        public void onMovieAvailable(Movie movie) {

            Log.i(TAG, movie.getTitle());
            textViewPaymentRowTitle.setText(movie.getTitle());
        }

        public void getMovie(String apiUrl) {

            SingleMovieApiConnector task = new SingleMovieApiConnector(this);
            String[] urls = new String[]{apiUrl};
            task.execute(urls);
        }
    }

}
