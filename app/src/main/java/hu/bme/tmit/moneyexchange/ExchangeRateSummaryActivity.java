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

        bundle = getIntent().getExtras();
        assert bundle != null;
        double rate = bundle.getDouble("amountHUF") / bundle.getDouble("amountEUR");

        if (rate < 299) {
            view.setBackgroundColor(Color.parseColor("#fb5763"));
            image.setImageResource(R.drawable.fail);
            rateText.setText("BAD RATE");
            rateTextExplain.setText("The proposed exchange rate is: \n" + String.format("%.2f", rate) +
                    " HUF/EUR\nWe do not recommend you to accept this rate.");
            btnSafeRate.setText("SAFE RATE ANYWAYS");
        } else {
            view.setBackgroundColor(Color.parseColor("#92c768"));
            image.setImageResource(R.drawable.success);
            rateText.setText("GOOD RATE");
            rateTextExplain.setText("The proposed exchange rate is: \n" + String.format("%.2f", rate) +
                    " HUF/EUR\nWe recommend you to accept this rate.");
            btnSafeRate.setText("SAFE RATE");
        }
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        float oldAmountHUF = sharedPreferences.getFloat("amountHUF", 0);
        float oldAmountEUR = sharedPreferences.getFloat("amountEUR", 0);

        editor.putFloat("amountHUF", oldAmountHUF + (float) bundle.getDouble("amountHUF"));
        editor.putFloat("amountEUR", oldAmountEUR + (float) bundle.getDouble("amountEUR"));
        editor.apply();
        finish();
    }
}
