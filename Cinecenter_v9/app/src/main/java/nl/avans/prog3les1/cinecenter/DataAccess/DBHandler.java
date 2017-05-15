package nl.avans.prog3les1.cinecenter.DataAccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nl.avans.prog3les1.cinecenter.Domain.Movie;
import nl.avans.prog3les1.cinecenter.Domain.Rate;
import nl.avans.prog3les1.cinecenter.Domain.Reservation;
import nl.avans.prog3les1.cinecenter.Domain.Review;
import nl.avans.prog3les1.cinecenter.Domain.Ticket;

/**
 * Created by marni on 28-3-2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private static final int DB_VERSION = 18;
    private static final String DB_NAME = "cinema.db";

    // Table names
    private static final String TABLE_RATE = "rate";
    private static final String TABLE_RESERVATION = "reservation";
    private static final String TABLE_TICKET = "ticket";
    private static final String TABLE_MOVIE = "movie";
    private static final String TABLE_SEAT = "seat";
    private static final String TABLE_REVIEW ="review";


    // Shared columns
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MOVIE_ID = "movieId";

    // Columns seat
    private static final String COLUMN_SEAT_NUMBER = "number";

    // Columns rate
    private static final String COLUMN_RATE_RATE = "rate";
    private static final String COLUMN_RATE_PRICE = "price";

    // Columns reservation
    private static final String COLUMN_RESERVATION_DATE = "date";
    private static final String COLUMN_RESERVATION_TIME = "time";
    private static final String COLUMN_RESERVATION_MOLLIE_PAYMENT_STATUS = "mollie_payment_status";
    private static final String COLUMN_RESERVATION_MOLLIE_PAYMENT_STATUS_ORIGINAL = "mollie_payment_status_original";
    private static final String COLUMN_RESERVATION_MOLLIE_PAYMENT_ID = "mollie_payment_id";
    private static final String COLUMN_RESERVATION_VISITOR_ID = "visitor_id";

    // Columns ticket
    private static final String COLUMN_TICKET_RATE_ID = "rateId";
    private static final String COLUMN_TICKET_SEAT_ID = "seatId";
    private static final String COLUMN_TICKET_RESERVATION_ID = "reservationId";

    //Columns review
    private static final String COLUMN_REVIEW_TEXT = "reviewText";
    private static final String COLUMN_REVIEW_RATING = "reviewRating";

    // Columns movie
    private static final String COLUMN_MOVIE_POSTER_IMAGE_URL = "posterImageUrl";

    // Constructor
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Requires methods
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG, "onCreate called.");

        String CREATE_TABLE;

        //Create table seat
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SEAT +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_SEAT_NUMBER + " INTEGER" +
                ")";

        db.execSQL(CREATE_TABLE);

        //insert 30 seats
        db.beginTransaction();
        try {
            for (int i = 1; i <= 30; i++) {
                String INSERT_SEATS = "INSERT INTO " + TABLE_SEAT + " (_id, number) VALUES('" + i + "','" + i + "')";
                db.execSQL(INSERT_SEATS);
            }

            // If successful commit those changes
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        //Create table rate
        CREATE_TABLE = "CREATE TABLE " + TABLE_RATE +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_RATE_RATE + " TEXT," +
                COLUMN_RATE_PRICE + " REAL" +
                ")";

        db.execSQL(CREATE_TABLE);

        //Create table reservation
        CREATE_TABLE = "CREATE TABLE " + TABLE_RESERVATION +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_RESERVATION_DATE + " TEXT, " +
                COLUMN_RESERVATION_TIME + " TEXT, " +
                COLUMN_RESERVATION_MOLLIE_PAYMENT_STATUS + " TEXT, " +
                COLUMN_RESERVATION_MOLLIE_PAYMENT_STATUS_ORIGINAL + " TEXT, " +
                COLUMN_RESERVATION_MOLLIE_PAYMENT_ID + " INTEGER, " +
                COLUMN_RESERVATION_VISITOR_ID + " INTEGER, " +
                COLUMN_MOVIE_ID + " INTEGER" +
                ")";

        Log.i(TAG, CREATE_TABLE);

        db.execSQL(CREATE_TABLE);

        //Create table ticket
        CREATE_TABLE = "CREATE TABLE " + TABLE_TICKET +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TICKET_RATE_ID + " INTEGER," +
                COLUMN_TICKET_SEAT_ID + " INTEGER," +
                COLUMN_TICKET_RESERVATION_ID + " INTEGER" +
                ")";

        db.execSQL(CREATE_TABLE);

        //insert example tickets and rates
        db.beginTransaction();
        try {
//            String INSERT_TICKET;
//
//            INSERT_TICKET = "INSERT INTO " + TABLE_TICKET + " (" + COLUMN_ID + ", " + COLUMN_TICKET_RATE_ID + ", " + COLUMN_TICKET_SEAT_ID + ", " + COLUMN_TICKET_RESERVATION_ID + ") VALUES(NULL, 1, " + 1 + ", 1)";
//            db.execSQL(INSERT_TICKET);
//            INSERT_TICKET = "INSERT INTO " + TABLE_TICKET + " (" + COLUMN_ID + ", " + COLUMN_TICKET_RATE_ID + ", " + COLUMN_TICKET_SEAT_ID + ", " + COLUMN_TICKET_RESERVATION_ID + ") VALUES(NULL, 1, " + 2 + ", 1)";
//            db.execSQL(INSERT_TICKET);

            String INSERT_RATE;

            INSERT_RATE = "INSERT INTO " + TABLE_RATE + " ('" + COLUMN_RATE_RATE + "', " + COLUMN_RATE_PRICE + ") VALUES ('Age 12 and under', 6.50);";
            db.execSQL(INSERT_RATE);
            INSERT_RATE = "INSERT INTO " + TABLE_RATE + " ('" + COLUMN_RATE_RATE + "', " + COLUMN_RATE_PRICE + ") VALUES ('Age 12 - 17', 9.00);";
            db.execSQL(INSERT_RATE);
            INSERT_RATE = "INSERT INTO " + TABLE_RATE + " ('" + COLUMN_RATE_RATE + "', " + COLUMN_RATE_PRICE + ") VALUES ('Regular', 12.50);";
            db.execSQL(INSERT_RATE);
            INSERT_RATE = "INSERT INTO " + TABLE_RATE + " ('" + COLUMN_RATE_RATE + "', " + COLUMN_RATE_PRICE + ") VALUES ('Age 65 and up', 7.50);";
            db.execSQL(INSERT_RATE);

            // If successful commit those changes
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        //Create table movie
        CREATE_TABLE = "CREATE TABLE " + TABLE_MOVIE +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_MOVIE_ID + " INTEGER," +
                COLUMN_MOVIE_POSTER_IMAGE_URL + " TEXT" +
                ")";

        db.execSQL(CREATE_TABLE);

        //Create table review
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_REVIEW +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_REVIEW_RATING + " INTEGER," +
                COLUMN_REVIEW_TEXT + " TEXT" +
                ")";

        db.execSQL(CREATE_TABLE);

        //insert example reviews
        db.beginTransaction();
        try
        {
            String INSERT_REVIEWS = "INSERT INTO "+ TABLE_REVIEW + " (" + COLUMN_REVIEW_RATING + ", " + COLUMN_REVIEW_TEXT + ") VALUES(5, 'Lorem ipsum dolor sit amet, nonummy ligula volutpat hac integer nonummy. Suspendisse ultricies, congue etiam tellus, erat libero, nulla eleifend, mauris pellentesque. Suspendisse integer praesent vel, integer gravida mauris, fringilla vehicula lacinia non')";
            db.execSQL(INSERT_REVIEWS);

            String INSERT_REVIEWS1 = "INSERT INTO "+ TABLE_REVIEW + " (" + COLUMN_REVIEW_RATING + ", " + COLUMN_REVIEW_TEXT + ") VALUES(4,'Lorem ipsum dolor sit amet, nonummy ligula volutpat hac integer nonummy. Suspendisse ultricies, congue etiam tellus, erat libero, nulla eleifend, mauris pellentesque. Suspendisse integer praesent vel, integer gravida mauris, fringilla vehicula lacinia non')";
            db.execSQL(INSERT_REVIEWS1);

            String INSERT_REVIEWS2 = "INSERT INTO "+ TABLE_REVIEW + " (" + COLUMN_REVIEW_RATING + ", " + COLUMN_REVIEW_TEXT + ") VALUES(3,'Lorem ipsum dolor sit amet, nonummy ligula volutpat hac integer nonummy. Suspendisse ultricies, congue etiam tellus, erat libero, nulla eleifend, mauris pellentesque. Suspendisse integer praesent vel, integer gravida mauris, fringilla vehicula lacinia non')";
            db.execSQL(INSERT_REVIEWS2);

            String INSERT_REVIEWS3 = "INSERT INTO "+ TABLE_REVIEW + " (" + COLUMN_REVIEW_RATING + ", " + COLUMN_REVIEW_TEXT + ") VALUES(2,'Lorem ipsum dolor sit amet, nonummy ligula volutpat hac integer nonummy. Suspendisse ultricies, congue etiam tellus, erat libero, nulla eleifend, mauris pellentesque. Suspendisse integer praesent vel, integer gravida mauris, fringilla vehicula lacinia non')";
            db.execSQL(INSERT_REVIEWS3);

            String INSERT_REVIEWS4 = "INSERT INTO "+ TABLE_REVIEW + " (" + COLUMN_REVIEW_RATING + ", " + COLUMN_REVIEW_TEXT + ") VALUES(1,'Lorem ipsum dolor sit amet, nonummy ligula volutpat hac integer nonummy. Suspendisse ultricies, congue etiam tellus, erat libero, nulla eleifend, mauris pellentesque. Suspendisse integer praesent vel, integer gravida mauris, fringilla vehicula lacinia non')";
            db.execSQL(INSERT_REVIEWS4);


            // If successful commit those changes
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        onCreate(db);
    }

    // Rate operations
    public Long addRate(Rate rate) {
        Log.i(TAG, "addRate " + rate);

        ContentValues values = new ContentValues();
        values.put(COLUMN_RATE_PRICE, rate.getPrice());
        values.put(COLUMN_RATE_RATE, rate.getRate());

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_RATE, null, values);
    }

    public ArrayList<Rate> getAllRates() {
        Log.i(TAG, "getAllRates");

        String query = "SELECT * FROM " + TABLE_RATE;

        Log.i(TAG, "Query: " + query);

        ArrayList<Rate> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Rate rate;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            rate = new Rate();
            rate.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            rate.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATE_PRICE)));
            rate.setRate(cursor.getString(cursor.getColumnIndex(COLUMN_RATE_RATE)));

            Log.i(TAG, "Found " + rate + ", adding to list");
            result.add(rate);
        }

        db.close();
        Log.i(TAG, "Returning " + result.size() + " items");
        return result;
    }

    public void deleteAllRates() {
        Log.i(TAG, "delete all rates");

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_RATE);
    }

    public Rate getRateById(String rateId) {

        Log.i(TAG, "getRateById");

        String query = "SELECT * FROM " + TABLE_RATE + " WHERE " + COLUMN_ID + " = " + rateId;

        Log.i(TAG, "Query: " + query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Rate rate = null;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            rate = new Rate();
            rate.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            rate.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_RATE_PRICE)));
            rate.setRate(cursor.getString(cursor.getColumnIndex(COLUMN_RATE_RATE)));

            Log.i(TAG, "Found " + rate + ", adding to list");
        }

        db.close();
        return rate;
    }

    // Reservation operations
    public Long addReservation(Reservation reservation) {
        Log.i(TAG, "addRate " + reservation);

        ContentValues values = new ContentValues();
        values.put(COLUMN_RESERVATION_DATE, reservation.getDate());
        values.put(COLUMN_RESERVATION_TIME, reservation.getTime());
        values.put(COLUMN_RESERVATION_MOLLIE_PAYMENT_ID, reservation.getMolliePaymentId());
        values.put(COLUMN_RESERVATION_MOLLIE_PAYMENT_STATUS, reservation.getMolliePaymentStatus());
        values.put(COLUMN_RESERVATION_MOLLIE_PAYMENT_STATUS_ORIGINAL, reservation.getMolliePaymentStatusOriginal());
        values.put(COLUMN_RESERVATION_VISITOR_ID, reservation.getVisitorId());
        values.put(COLUMN_MOVIE_ID, reservation.getMovieId());

        Log.i(TAG, "Reservation with id " + reservation.getId() + " created");

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_RESERVATION, null, values);
    }

    public void deleteAllReservations() {
        Log.i(TAG, "delete all reservations");

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_RESERVATION);
    }

    public void deleteAllTickets() {
        Log.i(TAG, "delete all tickets");

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_TICKET);
    }

    public Reservation getLastInsertedReservation() {

        Reservation reservation = new Reservation();

        String query = "SELECT last_insert_rowid() as id";

        Log.i(TAG, "Query: " + query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int id = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        reservation.setId(id + "");

        return reservation;
    }

    public ArrayList<Reservation> getAllReservations() {

        Log.i(TAG, "getAllReservations");

        String query = "SELECT * FROM " + TABLE_RESERVATION + " ORDER BY " + COLUMN_ID + " DESC";

        Log.i(TAG, "Query: " + query);

        ArrayList<Reservation> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Reservation reservation;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            reservation = new Reservation();
            reservation.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            reservation.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_DATE)));
            reservation.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_TIME)));
            reservation.setMovieId(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID)));

            Log.i(TAG, "Found " + reservation + ", adding to list");
            result.add(reservation);
        }

        db.close();
        Log.i(TAG, "Returning " + result.size() + " items");
        return result;
    }

    // Movie operations
    public int selectCountFromMovieByMovieId(int movieId) {

        int count = 0;

        String query = "SELECT count(*) as count FROM " + TABLE_MOVIE + " WHERE " + COLUMN_MOVIE_ID + " = " + movieId;

        Log.i(TAG, "Query: " + query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            count = cursor.getInt(cursor.getColumnIndex("count"));
        }

        return count;
    }

    public void addMovie(Movie movie) {
        Log.i(TAG, "addMovie " + movie);

        if (!(selectCountFromMovieByMovieId(Integer.parseInt(movie.getMovieId())) > 0)) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_MOVIE_ID, movie.getMovieId());
            values.put(COLUMN_MOVIE_POSTER_IMAGE_URL, movie.getPosterImageUrl());

            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_MOVIE, null, values);
        }
    }

    public ArrayList<Movie> getAllMovies() {

        Log.i(TAG, "getAllMovies");

        String query = "SELECT * FROM " + TABLE_MOVIE;

        Log.i(TAG, "Query: " + query);

        ArrayList<Movie> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Movie movie;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            movie = new Movie();
            movie.setMovieId(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID)));
            movie.setPosterImageUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_POSTER_IMAGE_URL)));

            Log.i(TAG, "Found " + movie.getTitle() + ", adding to list");
            result.add(movie);
        }

        db.close();
        Log.i(TAG, "Returning " + result.size() + " items");
        return result;
    }

    public void deleteAllMovies() {
        Log.i(TAG, "delete all movies");

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_MOVIE);
    }

    public int countSeats() {
        int seatsCount = 0;

        String query = "SELECT COUNT(*) FROM " + TABLE_SEAT;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            seatsCount = cursor.getInt(0);
        }

        cursor.close();

        return seatsCount;
    }

    public int getAmountOfTickets(int MovieId, String date, String time) {

        String query = "SELECT count(*) as count FROM " +
                TABLE_TICKET + " JOIN " +
                TABLE_RESERVATION + " ON (" +
                TABLE_TICKET + "." +
                COLUMN_TICKET_RESERVATION_ID + " = " +
                TABLE_RESERVATION + "." +
                COLUMN_ID + ") WHERE " +
                TABLE_RESERVATION + "." +
                COLUMN_MOVIE_ID + " = " + MovieId + " AND " +
                TABLE_RESERVATION + "." + COLUMN_RESERVATION_DATE + " = '" + date +  "' AND " +
                TABLE_RESERVATION + "." + COLUMN_RESERVATION_TIME + " = '" + time + "'";

        Log.i(TAG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        int count = 0;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            count = cursor.getInt(cursor.getColumnIndex("count"));
        }
        db.close();

        Log.i(TAG, count + "");
        return count;
    }

    // Ticket operations
    public Long addTicket(Ticket ticket) {
        Log.i(TAG, "addRate " + ticket);

        ContentValues values = new ContentValues();
        values.put(COLUMN_TICKET_SEAT_ID, ticket.getSeatId());
        values.put(COLUMN_TICKET_RATE_ID, ticket.getRateId());
        values.put(COLUMN_TICKET_RESERVATION_ID, ticket.getReservationId());

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_TICKET, null, values);
    }

    public ArrayList<Ticket> getTicketsByReservationId(String reservationId) {

        Log.i(TAG, "getTicketsByReservationId");

        String query = "SELECT * FROM " + TABLE_TICKET + " WHERE " + COLUMN_TICKET_RESERVATION_ID + " = " + reservationId;

        Log.i(TAG, "Query: " + query);

        ArrayList<Ticket> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Ticket ticket;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            ticket = new Ticket();
            ticket.setRateId(cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_RATE_ID)));
            ticket.setSeatId(cursor.getInt(cursor.getColumnIndex(COLUMN_TICKET_SEAT_ID)));
            ticket.setReservationId(cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_RESERVATION_ID)));

            Log.i(TAG, "Found " + ticket + ", adding to list");
            result.add(ticket);
        }

        db.close();
        Log.i(TAG, "Returning " + result.size() + " items");
        return result;
    }
    public List<Review> getAllReviews() {
        Log.i(TAG, "getAllReviews");

        String query = "SELECT * FROM " + TABLE_REVIEW;

        Log.i(TAG, "Query: " + query);

        ArrayList<Review> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Review review;


        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            review = new Review();
            review.setRating(cursor.getInt(cursor.getColumnIndex("reviewRating")));
            review.setReviewText(cursor.getString(cursor.getColumnIndex("reviewText")));


            Log.i(TAG, "Found " + review.getReviewAuthor() + ", adding to list");
            result.add(review);
        }

        db.close();
        Log.i(TAG, "Returning " + result.size() + " items");
        return result;
    }

    public long addReview(Review review) {
        Log.i(TAG, "addReview " + review);

        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_RATING, review.getRating());
        values.put(COLUMN_REVIEW_TEXT, review.getReviewText());

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_REVIEW, null, values);
    }

    public void deleteAllReviews() {
        Log.i(TAG, "delete all reviews");

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+ TABLE_REVIEW);
    }
}
