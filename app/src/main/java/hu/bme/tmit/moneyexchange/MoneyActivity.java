package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MoneyActivity extends Activity implements OnKeyListener, View.OnClickListener {

    TextView currencyInputHome;
    TextView currencyInputForeign;
    TextView summary;

    double currencyHome;
    double currencyForeign;

    Button btnCheck;

    Double exchangeRate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        currencyInputHome = findViewById(R.id.currencyInputHome);
        currencyInputHome.setOnKeyListener(this);
        currencyInputHome.setHint("0");
        currencyInputForeign = findViewById(R.id.currencyInputForeign);
        currencyInputForeign.requestFocus();
        currencyInputForeign.setHint("0");

        summary = findViewById(R.id.summary);
        summary.setFocusable(false);
        summary.setVisibility(View.INVISIBLE);

        btnCheck = findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {

            currencyForeign = Double.parseDouble(currencyInputForeign.getText().toString());
            currencyHome = Double.parseDouble(currencyInputHome.getText().toString());

            exchangeRate = (double)Math.round((currencyForeign / currencyHome * 100d) / 100d);
            summary.setText(String.valueOf(exchangeRate));

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            summary.requestFocus();
            summary.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        if (exchangeRate > 299) {
            i = new Intent(this, GoodRateActivity.class);
        } else {
            i = new Intent(this, BadRateActivity.class);
        }

        Bundle b = new Bundle();
        b.putDouble("rate", exchangeRate);
        i.putExtras(b);
        startActivity(i);
    }
}

