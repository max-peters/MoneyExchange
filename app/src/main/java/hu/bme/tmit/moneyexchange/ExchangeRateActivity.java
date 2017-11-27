package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ExchangeRateActivity extends Activity implements OnKeyListener, View.OnClickListener {

    TextView currencyInputEUR;
    TextView currencyInputHUF;

    double amountEUR;
    double amountHUF;

    Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_rate);
        currencyInputHUF = findViewById(R.id.inputHUF);
        currencyInputEUR = findViewById(R.id.inputEUR);
        currencyInputEUR.setOnKeyListener(this);

        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(this);

        currencyInputHUF.requestFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            boolean b = true;
            if (currencyInputEUR.getText().length() == 0) {
                currencyInputEUR.setError("This field can't be blank.");
                b = false;
            } else {
                amountEUR = Double.parseDouble(currencyInputEUR.getText().toString());
                if (amountEUR == 0) {
                    currencyInputEUR.setError("This field can't be zero.");
                    currencyInputEUR.setText("");
                    b = false;
                }
            }
            if (currencyInputHUF.getText().length() == 0) {
                currencyInputHUF.setError("This field can't be blank.");
                b = false;
            } else {
                amountHUF = Double.parseDouble(currencyInputHUF.getText().toString());
                if (amountHUF == 0) {
                    currencyInputHUF.setError("This field can't be zero.");
                    currencyInputHUF.setText("");
                    b = false;
                }
            }
            if (!b) return true;

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            Intent i = new Intent(this, ExchangeRateSummaryActivity.class);
            Bundle bu = new Bundle();
            bu.putDouble("amountEUR", amountEUR);
            bu.putDouble("amountHUF", amountHUF);
            i.putExtras(bu);
            startActivity(i);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

                Intent i = new Intent(this, MenuActivity.class);
                startActivity(i);
    }
}

