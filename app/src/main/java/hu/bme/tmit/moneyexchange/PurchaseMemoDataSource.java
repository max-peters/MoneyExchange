package hu.bme.tmit.moneyexchange;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class PurchaseMemoDataSource {

    private static final String LOG_TAG = PurchaseMemoDataSource.class.getSimpleName();
    private static PurchaseMemoDataSource Instance = null;
    private SQLiteDatabase database;
    private PurchaseMemoDbHelper dbHelper;

    private String[] columns = {
            PurchaseMemoDbHelper.COLUMN_ID,
            PurchaseMemoDbHelper.COLUMN_PRODUCT,
            PurchaseMemoDbHelper.COLUMN_DATE,
            PurchaseMemoDbHelper.COLUMN_PRICE
    };


    private PurchaseMemoDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new PurchaseMemoDbHelper(context);
    }

    public static PurchaseMemoDataSource getInstance(Context context) {
        if (Instance == null) {
            Instance = new PurchaseMemoDataSource(context);
        }
        return Instance;
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public PurchaseMemo createPurchaseMemo(String product, String date, Double price) {
        ContentValues values = new ContentValues();
        values.put(PurchaseMemoDbHelper.COLUMN_PRODUCT, product);
        values.put(PurchaseMemoDbHelper.COLUMN_DATE, date);
        values.put(PurchaseMemoDbHelper.COLUMN_PRICE, price);
        long insertId = database.insert(PurchaseMemoDbHelper.TABLE_PURCHASE_LIST, null, values);

        Cursor cursor = database.query(PurchaseMemoDbHelper.TABLE_PURCHASE_LIST,
                columns, PurchaseMemoDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        PurchaseMemo purchaseMemo = cursorToPurchaseMemo(cursor);
        cursor.close();

        return purchaseMemo;
    }

    private PurchaseMemo cursorToPurchaseMemo(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(PurchaseMemoDbHelper.COLUMN_ID);
        int idProduct = cursor.getColumnIndex(PurchaseMemoDbHelper.COLUMN_PRODUCT);
        int idDate= cursor.getColumnIndex(PurchaseMemoDbHelper.COLUMN_DATE);
        int idPrice= cursor.getColumnIndex(PurchaseMemoDbHelper.COLUMN_PRICE);

        String product = cursor.getString(idProduct);
        String date = cursor.getString(idDate);
        Double price = cursor.getDouble(idPrice);
        long id = cursor.getLong(idIndex);

        PurchaseMemo purchaseMemo = new PurchaseMemo(product, date, price, id);

        return purchaseMemo;
    }

    public List<PurchaseMemo> getAllPurchaseMemos() {
        List<PurchaseMemo> purchaseMemoList = new ArrayList<>();

        Cursor cursor = database.query(PurchaseMemoDbHelper.TABLE_PURCHASE_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        PurchaseMemo purchaseMemo;

        while(!cursor.isAfterLast()) {
            purchaseMemo = cursorToPurchaseMemo(cursor);
            purchaseMemoList.add(purchaseMemo);
            Log.d(LOG_TAG, "ID: " + purchaseMemo.getID() + ", Inhalt: " + purchaseMemo.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return purchaseMemoList;
    }

    public double getTotalSpendings(){

        Cursor cur = database.rawQuery("SELECT SUM(price) FROM purchase_list", null);
        if(cur.moveToFirst())
        {
            return cur.getDouble(0);
        }

    return -1;

    }

}