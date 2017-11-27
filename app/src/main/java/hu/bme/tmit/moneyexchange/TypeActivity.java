package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TypeActivity extends Activity implements View.OnClickListener {

    PurchaseMemoDataSource dataSource;
    TextView textViewTypeExplain;
    Button btnTypeRestaurant;
    Button btnTypeSightseeing;
    Button btnTypeAccommodation;
    Button btnTypeTransport;
    Button btnTypeOther;

    Bundle bundle;
    double amountHUF;
    double amountEUR;
    String printDate;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_activity);

        textViewTypeExplain = findViewById(R.id.textViewTypeExplain);

        btnTypeRestaurant = findViewById(R.id.btnTypeRestaurant);
        btnTypeRestaurant.setOnClickListener(this);

        btnTypeSightseeing = findViewById(R.id.btnTypeSightseeing);
        btnTypeSightseeing.setOnClickListener(this);

        btnTypeAccommodation = findViewById(R.id.btnTypeAccommodation);
        btnTypeAccommodation.setOnClickListener(this);

        btnTypeTransport = findViewById(R.id.btnTypeTransport);
        btnTypeTransport.setOnClickListener(this);

        btnTypeOther = findViewById(R.id.btnTypeOther);
        btnTypeOther.setOnClickListener(this);

        bundle = getIntent().getExtras();
        assert bundle != null;
        amountHUF = bundle.getDouble("amountHUF");
        amountEUR = bundle.getDouble("amountEUR");
        printDate = bundle.getString("date");

        dataSource = PurchaseMemoDataSource.getInstance(this);



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnTypeRestaurant:
                type = "food and drinks";
                storePurchase();
                break;
        }
        switch (v.getId()) {
            case R.id.btnTypeSightseeing:
                type = "sightseeing";
                storePurchase();
                break;
        }
        switch (v.getId()) {
            case R.id.btnTypeAccommodation:
                type = "accommodation";
                storePurchase();
                break;
        }
        switch (v.getId()) {
            case R.id.btnTypeTransport:
                type = "transport";
                storePurchase();
                break;
        }
        switch (v.getId()) {
            case R.id.btnTypeOther:
                type = "other ";
                storePurchase();
                break;
        }
    }

    private void storePurchase() {

        dataSource.open();
        dataSource.createPurchaseMemo(type, printDate, amountHUF, amountEUR);
        dataSource.close();
        finish();
    }
}
