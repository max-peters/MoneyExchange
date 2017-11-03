package hu.bme.tmit.moneyexchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoneyExchangeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCalc0;
    Button btnCalc1;
    Button btnCalc2;
    Button btnCalc3;
    Button btnCalc4;
    Button btnCalc5;
    Button btnCalc6;
    Button btnCalc7;
    Button btnCalc8;
    Button btnCalc9;
    Button btnCalcBack;
    Button btnCalcDott;
    Button btnCheck;
    TextView amountCurrencyOne;
    TextView amountCurrencyTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_exchange);

        btnCalc0 = findViewById(R.id.btnCalc0);
        btnCalc0.setOnClickListener(this);

        btnCalc1 = findViewById(R.id.btnCalc1);
        btnCalc1.setOnClickListener(this);

        btnCalc2 = findViewById(R.id.btnCalc2);
        btnCalc2.setOnClickListener(this);

        btnCalc3 = findViewById(R.id.btnCalc3);
        btnCalc3.setOnClickListener(this);

        btnCalc4 = findViewById(R.id.btnCalc4);
        btnCalc4.setOnClickListener(this);

        btnCalc5 = findViewById(R.id.btnCalc5);
        btnCalc5.setOnClickListener(this);

        btnCalc6 = findViewById(R.id.btnCalc6);
        btnCalc6.setOnClickListener(this);

        btnCalc7 = findViewById(R.id.btnCalc7);
        btnCalc7.setOnClickListener(this);

        btnCalc8 = findViewById(R.id.btnCalc8);
        btnCalc8.setOnClickListener(this);

        btnCalc9 = findViewById(R.id.btnCalc9);
        btnCalc9.setOnClickListener(this);

        btnCalcBack = findViewById(R.id.btnCalcBack);
        btnCalcBack.setOnClickListener(this);

        btnCalcDott = findViewById(R.id.btnCalcDott);
        btnCalcDott.setOnClickListener(this);

        btnCheck = findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(this);

        amountCurrencyOne = findViewById(R.id.amountCurrencyOne);
        amountCurrencyOne.setOnClickListener(this);

        amountCurrencyTwo = findViewById(R.id.amountCurrencyTwo);
        amountCurrencyTwo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnCalc0:
                amountCurrencyOne.setText("0");
                break;

            case R.id.btnCalc1:
                amountCurrencyOne.setText("1");
                break;

            case R.id.btnCalc2:
                amountCurrencyOne.setText("2");
                break;

            case R.id.btnCalc3:
                amountCurrencyOne.setText("3");
                break;

            case R.id.btnCalc4:
                amountCurrencyOne.setText("4");
                break;

            case R.id.btnCalc5:
                amountCurrencyOne.setText("5");
                break;

            case R.id.btnCalc6:
                amountCurrencyOne.setText("6");
                break;

            case R.id.btnCalc7:
                amountCurrencyOne.setText("7");
                break;

            case R.id.btnCalc8:
                amountCurrencyOne.setText("8");
                break;

            case R.id.btnCalc9:
                amountCurrencyOne.setText("9");
                break;

            case R.id.btnCalcBack:
                amountCurrencyOne.clearComposingText();
                break;

            case R.id.btnCalcDott:
                amountCurrencyOne.setText(",");
                break;

            case R.id.btnCheck:
                System.out.println("Juchu");
                break;

        }

    }
}
