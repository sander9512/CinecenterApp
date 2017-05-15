package nl.avans.prog3les1.cinecenter.Presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import nl.avans.prog3les1.cinecenter.R;

public class GeneralInfoActivity extends AppCompatActivity {
    private TextView weblink;
    private TextView routeLink;
    private RatingBar ratingGeneralInfoView;
    private Button btn;
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weblink = (TextView) findViewById(R.id.aboutWebsiteLink);
        if (weblink != null) {
            weblink.setMovementMethod(LinkMovementMethod.getInstance());
        }

        routeLink = (TextView) findViewById(R.id.googleMapsLink);
        if (routeLink != null) {
            routeLink.setMovementMethod(LinkMovementMethod.getInstance());
        }

        btn = (Button) findViewById(R.id.aboutRateUsHeader);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ReviewActivity.class);
                startActivity(i);
            }
        });
        ratingGeneralInfoView = (RatingBar) findViewById(R.id.ratingGeneralInfoView);
        ratingGeneralInfoView.setRating(5);
    }
}