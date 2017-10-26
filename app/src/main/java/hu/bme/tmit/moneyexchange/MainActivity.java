package hu.bme.tmit.moneyexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        money = findViewById(R.id.money);
        money.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, MoneyActivity.class);
        startActivity(i);
    }
}
