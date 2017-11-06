package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.TextView;

public class MoneyActivity extends Activity implements OnKeyListener {

    TextView currencyInputHome;
    TextView currencyInputForeign;
    TextView summary;

    double currencyHome;
    double currencyForeign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        currencyInputHome = findViewById(R.id.currencyInputHome);
        currencyInputHome.setOnKeyListener(this);
        currencyInputHome.setHint("enter EUR");
        currencyInputForeign = findViewById(R.id.currencyInputForeign);
        currencyInputForeign.requestFocus();
        currencyInputForeign.setHint("enter HUF");

        summary = findViewById(R.id.summary);
        summary.setFocusable(false);
        summary.setVisibility(View.INVISIBLE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            double exchangeRate;

            currencyForeign = Double.parseDouble(currencyInputForeign.getText().toString());
            currencyHome = Double.parseDouble(currencyInputHome.getText().toString());

            exchangeRate = ((int) (currencyForeign / currencyHome * 100) / 100.0);
            summary.setText(String.valueOf(exchangeRate));

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            summary.requestFocus();
            summary.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }
}
