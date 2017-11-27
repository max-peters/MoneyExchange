package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
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
    Button btnBackToExchange;
    ImageView image;
    TextView rateText;
    TextView rateTextExplain;
    View view;
    Bundle bundle;


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
        btnBackToExchange = findViewById(R.id.btnBackToExchange);
        btnBackToExchange.setOnClickListener(this);

        bundle = getIntent().getExtras();
        assert bundle != null;
        double rate = bundle.getDouble("amountHUF") / bundle.getDouble("amountEUR");

        if (rate < 299) {
            view.setBackgroundColor(Color.parseColor("#fb5763"));
            image.setImageResource(R.drawable.fail);
            rateText.setText("BAD RATE");
            rateTextExplain.setText("The proposed exchange rate is: \n" + String.format("%.2f", rate) +
                    " HUF/EUR\n\nWe do not recommend you to accept this rate.");
            btnSafeRate.setText("SAFE RATE ANYWAYS");
            btnSafeRate.setBackgroundColor(Color.parseColor("#FFAB3A3A"));
            btnBackToExchange.setBackgroundColor(Color.parseColor("#FFAB3A3A"));
        } else {
            view.setBackgroundColor(Color.parseColor("#92c768"));
            image.setImageResource(R.drawable.success);
            rateText.setText("GOOD RATE");
            rateTextExplain.setText("The proposed exchange rate is: \n" + String.format("%.2f", rate) +
                    " HUF/EUR\n\nWe recommend to accept this rate.");
            btnSafeRate.setText("SAFE RATE");
            btnSafeRate.setBackgroundColor(Color.parseColor("#FF53863E"));
            btnBackToExchange.setBackgroundColor(Color.parseColor("#FF53863E"));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSafeRate:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();

                float oldAmountHUF = sharedPreferences.getFloat("amountHUF", 0);
                float oldAmountEUR = sharedPreferences.getFloat("amountEUR", 0);

                editor.putFloat("amountHUF", oldAmountHUF + (float) bundle.getDouble("amountHUF"));
                editor.putFloat("amountEUR", oldAmountEUR + (float) bundle.getDouble("amountEUR"));
                editor.apply();
                finish();
                break;

            case R.id.btnBackToExchange:
                Intent i = new Intent(this, ExchangeRateActivity.class);
                startActivity(i);
                break;
        }

    }
}
