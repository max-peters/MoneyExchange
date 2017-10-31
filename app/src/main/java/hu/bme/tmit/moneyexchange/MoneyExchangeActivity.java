package hu.bme.tmit.moneyexchange;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MoneyExchangeActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_exchange);

        textView = findViewById(R.id.textView);
        System.out.println("Hllo");

    }
}
