package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.TextView;

public class ExchangeRateActivity extends Activity implements OnKeyListener {

    TextView currencyInputHome;
    TextView currencyInputForeign;

    double currencyHome;
    double currencyForeign;
    double exchangeRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);

        currencyInputForeign = findViewById(R.id.inputHUF);
        currencyInputForeign.requestFocus();
        currencyInputHome = findViewById(R.id.inputEUR);
        currencyInputHome.setOnKeyListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {

            currencyForeign = Double.parseDouble(currencyInputForeign.getText().toString());
            currencyHome = Double.parseDouble(currencyInputHome.getText().toString());

            exchangeRate = (double)Math.round((currencyForeign / currencyHome * 100d) / 100d);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            Intent i = new Intent(this, ExchangeRateSummaryActivity.class);
            Bundle b = new Bundle();
            b.putDouble("rate", exchangeRate);
            i.putExtras(b);
            startActivity(i);
            finish();
            return true;
        }
        return false;
    }
}

