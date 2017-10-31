package hu.bme.tmit.moneyexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMoneyExchange;
    Button btnPurchases;
    Button btnSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMoneyExchange = findViewById(R.id.btnMoneyExchange);
        btnMoneyExchange.setOnClickListener(this);

        btnPurchases = findViewById(R.id.btnPurchases);
        btnPurchases.setOnClickListener(this);

        btnSummary = findViewById(R.id.btnSummary);
        btnSummary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnMoneyExchange:
                Intent i1 = new Intent(this, MoneyExchangeActivity.class);
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
}
