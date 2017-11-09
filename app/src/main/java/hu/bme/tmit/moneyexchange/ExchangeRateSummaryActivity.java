package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExchangeRateSummaryActivity extends Activity implements View.OnClickListener {

    Button btnSafeRate;
    ImageView image;
    TextView rateText;
    TextView rateTextExplain;
    View view;
    double rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate_summary);

        view = findViewById(R.id.relativeLayout);
        image = findViewById(R.id.rateImage);
        rateText = findViewById(R.id.rateText);
        rateTextExplain = findViewById(R.id.rateTextExplain);
        btnSafeRate = findViewById(R.id.btnSafeRate);
        btnSafeRate.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        rate = b.getDouble("rate");

        if (rate < 299) {
            view.setBackgroundColor(Color.parseColor("#fb5763"));
            image.setImageResource(R.drawable.fail);
            rateText.setText("BAD RATE");
            rateTextExplain.setText("The proposed exchange rate is: \n" + String.valueOf(rate) +
                    " HUF/EUR\nWe do not recommend you to accept this rate.");
            btnSafeRate.setText("SAFE RATE ANYWAYS");
        } else {
            view.setBackgroundColor(Color.parseColor("#92c768"));
            image.setImageResource(R.drawable.success);
            rateText.setText("GOOD RATE");
            rateTextExplain.setText("The proposed exchange rate is: \n" + String.valueOf(rate) +
                    " HUF/EUR\nWe recommend you to accept this rate.");
            btnSafeRate.setText("SAFE RATE");
        }
    }

    @Override
    public void onClick(View v) {
        SavePreferences("currentRate", String.valueOf(rate));
        finish();
    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
