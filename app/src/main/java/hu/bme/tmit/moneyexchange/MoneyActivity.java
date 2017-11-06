package hu.bme.tmit.moneyexchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.TextView;

public class MoneyActivity extends AppCompatActivity implements OnKeyListener {

    TextView currencyInputHome;
    TextView currencyInputForeign;
    TextView summary;

    double currencyHome;
    double currencyForeign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        currencyInputForeign = findViewById(R.id.currencyInputForeign);
        summary = findViewById(R.id.summary);
        summary.setFocusable(false);

        currencyInputHome = findViewById(R.id.currencyInputHome);
        currencyInputForeign.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        currencyInputHome.setOnKeyListener(this);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            currencyForeign = Double.parseDouble(currencyInputForeign.getText().toString());
            currencyHome = Double.parseDouble(currencyInputHome.getText().toString());
            summary.setText(String.valueOf(currencyForeign / currencyHome));
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            summary.requestFocus();
            return true;
        }
        return false;
    }
}
