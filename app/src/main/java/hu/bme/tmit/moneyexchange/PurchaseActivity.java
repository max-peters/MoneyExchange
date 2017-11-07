package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class PurchaseActivity extends Activity implements View.OnKeyListener, View.OnClickListener{

    TextView currentRate;
    TextView purchaseHUFinput;
    TextView purchaseEuroValue;
    SharedPreferences sharedPreferences;
    double purchhaseHUF;
    double purchaseEURO;
    double rate;
    private PurchaseMemoDataSource dataSource;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    Button btnAddPurchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        purchaseHUFinput = findViewById(R.id.editPurchaseHUF);
        purchaseHUFinput.setOnKeyListener(this);
        purchaseHUFinput.setHint("0");

        purchaseEuroValue = findViewById(R.id.textViewPurchaseEuroValue);

        currentRate = findViewById(R.id.textCurrentRate);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String  data = sharedPreferences.getString("currentRate", "000") ;
        currentRate.setText(data);
        rate = Double.parseDouble(data);

        dataSource = new PurchaseMemoDataSource(this);

        btnAddPurchase = findViewById(R.id.btnAddPurchase);
        btnAddPurchase.setOnClickListener(this);


    }


    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {

            purchhaseHUF = Double.parseDouble(purchaseHUFinput.getText().toString());

            purchaseEURO = (double)Math.round((purchhaseHUF / rate * 100d) / 100d);
            purchaseEuroValue.setText(String.valueOf(purchaseEURO));

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            purchaseEuroValue.requestFocus();
            purchaseEuroValue.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Date currentDate = new Date();
        String printDate = sdf.format(currentDate).toString();

        PurchaseMemo purchaseMemo = dataSource.createPurchaseMemo("Test", printDate, purchaseEURO);
        Log.d(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(LOG_TAG, "ID: " + purchaseMemo.getID()+ ", Inhalt: " + purchaseMemo.toString());


        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);

    }



}
