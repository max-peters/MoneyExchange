package hu.bme.tmit.moneyexchange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread myThread = new Thread() {

            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
