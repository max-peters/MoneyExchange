package hu.bme.tmit.moneyexchange;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PurchaseMemoDbHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "purchase_list.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_PURCHASE_LIST = "purchase_list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PRICE_HUF = "priceHUF";
    public static final String COLUMN_PRICE_EUR = "priceEUR";
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_PURCHASE_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCT + " TEXT NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_PRICE_HUF + " DOUBLE NOT NULL, " +
                    COLUMN_PRICE_EUR + " DOUBLE NOT NULL);";
    private static final String LOG_TAG = PurchaseMemoDbHelper.class.getSimpleName();


    public PurchaseMemoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
        onCreate(getWritableDatabase());
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}