package hu.bme.tmit.moneyexchange;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
        registerForContextMenu(purchaseMemosListView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setUp();
        if (purchaseMemoList.isEmpty() && sharedPreferences.getFloat("amountHUF", 0) == 0
                && sharedPreferences.getFloat("amountEUR", 0) == 0) {
            Toast toast = Toast.makeText(this,
                    "save a withdrawal or purchase first",
                    Toast.LENGTH_SHORT);
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
        ArrayAdapter<PurchaseMemo> purchaseMemoArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                purchaseMemoList);
        purchaseMemosListView.setAdapter(purchaseMemoArrayAdapter);
        Double totalAmount = dataSource.getTotalSpendings();
        summary.setText("total amount spent: " + String.format("%.2f", totalAmount) + " EUR\n" +
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
                editor.apply();
                dataSource.reset();
                dataSource.close();
                setUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listViewPurchases) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                PurchaseMemo memo = purchaseMemoList.get(info.position);
                dataSource.open();
                dataSource.delete(memo);
                dataSource.close();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("amountHUF", sharedPreferences.getFloat("amountHUF", 0) + (float) memo.getPriceHUF());
                editor.putFloat("amountEUR", sharedPreferences.getFloat("amountEUR", 0) + (float) memo.getPriceEUR());
                editor.apply();
                setUp();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
