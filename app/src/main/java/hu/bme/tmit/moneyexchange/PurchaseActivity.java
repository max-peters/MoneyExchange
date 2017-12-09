package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PurchaseActivity extends Activity implements View.OnKeyListener, View.OnClickListener {

    TextView textAmountEUR;
    TextView purchaseHUF;
    TextView textRate;
    Button btnAddPurchase;
    SharedPreferences sharedPreferences;
    float totalAmountHUF;
    float totalAmountEUR;
    double amountHUF;
    double amountEUR;
    double rate;

    PurchaseMemoDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        purchaseHUF = findViewById(R.id.editPurchaseHUF);
        purchaseHUF.setOnKeyListener(this);
        textRate = findViewById(R.id.textRate);
        textAmountEUR = findViewById(R.id.textAmountEUR);
        btnAddPurchase = findViewById(R.id.btnAddPurchase);
        btnAddPurchase.setOnClickListener(this);

        dataSource = PurchaseMemoDataSource.getInstance(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (sharedPreferences.getFloat("amountEUR", 0) == 0 && sharedPreferences.getFloat("amountHUF", 0) == 0) {
            Toast toast = Toast.makeText(this,
                    "save a withdrawal before purchasing",
                    Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }

        rate = sharedPreferences.getFloat("amountHUF", 0) / sharedPreferences.getFloat("amountEUR", 0);
        totalAmountHUF = sharedPreferences.getFloat("amountHUF", 0);
        totalAmountEUR = sharedPreferences.getFloat("amountEUR", 0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        textRate.setText("Current rate is " + String.format("%.2f", rate) + " HUF/EUR.");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            if (purchaseHUF.getText().length() == 0) {
                purchaseHUF.setError("This field can't be blank.");
                return true;
            } else {
                amountHUF = Double.parseDouble(purchaseHUF.getText().toString());
                if (amountHUF == 0) {
                    purchaseHUF.setError("This field can't be zero.");
                    purchaseHUF.setText("");
                    return true;
                }
            }
            amountEUR = amountHUF / rate;
            textAmountEUR.setText("You spent " + String.format("%.2f", amountEUR) + " EUR.");
            if (totalAmountHUF - amountHUF < 0) {
                Toast toast = Toast.makeText(this,
                        "This purchase exceeds the stored amount of HUF! Therefore you can't save it.",
                        Toast.LENGTH_LONG);
                toast.show();
                btnAddPurchase.setVisibility(View.INVISIBLE);
            } else {
                btnAddPurchase.setVisibility(View.VISIBLE);
            }
            purchaseHUF.clearFocus();
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        Date currentDate = Calendar.getInstance().getTime();
        String printDate = new SimpleDateFormat("yyyy/MM/dd").format(currentDate);

        Intent i = new Intent(this, TypeActivity.class);

        Bundle bu = new Bundle();
            bu.putString("date", printDate);
            bu.putDouble("amountHUF", amountHUF);
            bu.putDouble("amountEUR", amountEUR);
            i.putExtras(bu);

        startActivity(i);
        finish();

    }
}
