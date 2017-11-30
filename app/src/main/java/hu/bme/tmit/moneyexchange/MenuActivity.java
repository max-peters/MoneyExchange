package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity implements View.OnClickListener {

    Button btnMoneyExchange;
    Button btnPurchases;
    Button btnSummary;
    TextView currentAmount;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        btnMoneyExchange = findViewById(R.id.btnMoneyExchange);
        btnMoneyExchange.setOnClickListener(this);

        btnPurchases = findViewById(R.id.btnPurchases);
        btnPurchases.setOnClickListener(this);

        btnSummary = findViewById(R.id.btnSummary);
        btnSummary.setOnClickListener(this);

        currentAmount = findViewById(R.id.textViewCurrentAmount);
        currentAmount.setText("Current amount: " + Math.round(sharedPreferences.getFloat("amountHUF", 0)) + " HUF");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMoneyExchange:
                Intent i1 = new Intent(this, ExchangeRateActivity.class);
                startActivity(i1);
                break;

            case R.id.btnPurchases:
                Intent i2 = new Intent(this, PurchaseActivity.class);
                startActivity(i2);
                break;

            case R.id.btnSummary:
                Intent i3 = new Intent(this, SummaryActivity.class);
                startActivity(i3);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentAmount.setText("Current amount: " + Math.round(sharedPreferences.getFloat("amountHUF", 0)) + " HUF");
    }
}
