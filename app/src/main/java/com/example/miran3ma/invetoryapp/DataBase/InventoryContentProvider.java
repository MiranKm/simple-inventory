package com.example.miran3ma.invetoryapp.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.*;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.*;

/**
 * Created by miran on 11/12/2017.
 */

public class InventoryContentProvider extends ContentProvider {
    DataBaseHelper helper;
    public static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY, TABLE_NAME, 1);
        matcher.addURI(AUTHORITY, TABLE_NAME + "/#", 2);
    }

    @Override
    public boolean onCreate() {
        helper = new DataBaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        int code = matcher.match(uri);

        Cursor cursor = null;

        String[] columns = {ID, ITEM_NAME, SUPPLIER, PRICE, COUNT, IMAGE};

        if (code == 1) {
            cursor = helper.getReadableDatabase().query(TABLE_NAME, columns, null, null, null, null, null);
        } else if (code == 2) {
            long id = ContentUris.parseId(uri);
            String[] args = {String.valueOf(id)};
            cursor = helper.getReadableDatabase().query(TABLE_NAME, columns, ID + " = ?", args, null, null, null);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        helper.getWritableDatabase().insert(TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        helper.getWritableDatabase().delete(TABLE_NAME, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String
            s, @Nullable String[] strings) {
        helper.getWritableDatabase().update(TABLE_NAME, values, s, strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
