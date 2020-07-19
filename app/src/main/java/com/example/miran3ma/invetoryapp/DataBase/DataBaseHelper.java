package com.example.miran3ma.invetoryapp.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.*;

/**
 * Created by miran on 11/12/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context){
        super(context, InventoryContract.DATABASE,null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY, " + ITEM_NAME +
                " TEXT, " + SUPPLIER + " TEXT, " + PRICE + " INTEGER," + COUNT +" INTEGER,"+IMAGE+" TEXT);");;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
