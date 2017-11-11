package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SummaryActivity extends Activity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    ListView purchaseMemosListView;
    TextView summary;
    private PurchaseMemoDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        dataSource = PurchaseMemoDataSource.getInstance(this);

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        List<PurchaseMemo> purchaseMemoList = dataSource.getAllPurchaseMemos();

        ArrayAdapter<PurchaseMemo> purchaseMemoArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                purchaseMemoList);

        purchaseMemosListView = findViewById(R.id.listViewPurchases);
        purchaseMemosListView.setAdapter(purchaseMemoArrayAdapter);

        Double totalAmount = dataSource.getTotalSpendings();
        summary = findViewById(R.id.summary);
        summary.setText("total amount spent: " + String.valueOf(Math.round(totalAmount * 100) / 100d) + " EUR\n" +
                "amount left: " + Math.round(sharedPreferences.getFloat("amountHUF", 0)) + " HUF");

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }


}
