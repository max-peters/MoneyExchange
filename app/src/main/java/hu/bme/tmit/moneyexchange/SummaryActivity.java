package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class SummaryActivity extends Activity {

    private PurchaseMemoDataSource dataSource;
    ListView purchaseMemosListView;
    TextView totalSpendings;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        dataSource = new PurchaseMemoDataSource(this);

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        List<PurchaseMemo> purchaseMemoList = dataSource.getAllPurchaseMemos();

        ArrayAdapter<PurchaseMemo> purchaseMemoArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                purchaseMemoList);

        purchaseMemosListView = findViewById(R.id.listViewPurchases);
        purchaseMemosListView.setAdapter(purchaseMemoArrayAdapter);

        Double totalAmount = dataSource.getTotalSpendings();
        totalSpendings = findViewById(R.id.totalSpendings);
        totalSpendings.setText(totalAmount.toString());

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }


}
