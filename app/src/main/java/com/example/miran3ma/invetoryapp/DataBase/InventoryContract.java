package com.example.miran3ma.invetoryapp.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by miran on 11/12/2017.
 */

public class InventoryContract {

    public static final String AUTHORITY = "com.example.miran3ma.invetoryapp";
    public static final String DATABASE = "inventory.db";

    public static final class ItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "Items";

        public static final String ID = BaseColumns._ID;
        public static final String ITEM_NAME = "name";
        public static final String PRICE = "price";
        public static final String COUNT = "count";
        public static final String SUPPLIER = "supplier";
        public static final String IMAGE = "image";

        public static final Uri URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    }
}
