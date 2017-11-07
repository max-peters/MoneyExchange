package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BadRateActivity extends Activity implements View.OnClickListener {

    Button btnSafeRate;
    Double rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_rate);

        btnSafeRate = findViewById(R.id.btnSafeRate2);
        btnSafeRate.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        rate = b.getDouble("rate");
    }

    @Override
    public void onClick(View v){
        SavePreferences("currentRate", rate.toString());

    }

    private void SavePreferences(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }
}
