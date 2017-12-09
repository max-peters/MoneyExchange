package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TypeActivity extends Activity implements View.OnClickListener {

    PurchaseMemoDataSource dataSource;
    SharedPreferences sharedPreferences;
    TextView editTextOwnType;
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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        editTextOwnType = findViewById(R.id.editTextOwnType);

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

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
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
                String other = editTextOwnType.getText().toString();
                if (other.isEmpty()) {
                    editTextOwnType.setError("this field can't be empty");
                } else {
                    type = other;
                    storePurchase();
                }
                break;
        }
    }

    private void storePurchase() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("amountHUF", sharedPreferences.getFloat("amountHUF", 0) - (float) amountHUF);
        editor.putFloat("amountEUR", sharedPreferences.getFloat("amountEUR", 0) - (float) amountEUR);
        editor.apply();

        dataSource.open();
        dataSource.createPurchaseMemo(type, printDate, amountHUF, amountEUR);
        dataSource.close();
        finish();
    }
}
