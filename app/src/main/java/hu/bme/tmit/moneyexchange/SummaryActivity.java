package hu.bme.tmit.moneyexchange;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    ListView purchaseMemosListView;
    TextView summary;
    List<PurchaseMemo> purchaseMemoList;
    PurchaseMemoDataSource dataSource;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        summary = findViewById(R.id.summary);
        purchaseMemosListView = findViewById(R.id.listViewPurchases);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setUp();
        if (purchaseMemoList.isEmpty() && sharedPreferences.getFloat("amountHUF", 0) == 0
                && sharedPreferences.getFloat("amountEUR", 0) == 0) {
            Toast toast = Toast.makeText(this,
                    "save a withdrawal or purchase first",
                    Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        setTitle("Summary");
    }

    private void setUp() {
        dataSource = PurchaseMemoDataSource.getInstance(this);
        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();
        purchaseMemoList = dataSource.getAllPurchaseMemos();
        ArrayAdapter<PurchaseMemo> purchaseMemoArrayAdapter = new ArrayAdapter<> (
                this,
                android.R.layout.simple_list_item_multiple_choice,
                purchaseMemoList);
        purchaseMemosListView.setAdapter(purchaseMemoArrayAdapter);
        Double totalAmount = dataSource.getTotalSpendings();
        summary.setText("total amount spent: " + String.valueOf(Math.round(totalAmount * 100) / 100d) + " EUR\n" +
                "amount left: " + Math.round(sharedPreferences.getFloat("amountHUF", 0)) + " HUF");
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                dataSource.open();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("amountHUF", 0);
                editor.putFloat("amountEUR", 0);
                editor.commit();
                dataSource.reset();
                dataSource.close();
                setUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
