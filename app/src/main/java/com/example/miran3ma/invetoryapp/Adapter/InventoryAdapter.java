package com.example.miran3ma.invetoryapp.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.miran3ma.invetoryapp.R;

import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.COUNT;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.ID;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.ITEM_NAME;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.PRICE;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.SUPPLIER;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.URI;

/**
 * Created by miran on 11/12/2017.
 */

public class InventoryAdapter extends CursorAdapter {

    int quantityMinusOne;

    public InventoryAdapter(Context context) {
        super(context, null, 0);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.items_sale, null);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView id = view.findViewById(R.id.id);
        TextView itemName = view.findViewById(R.id.item_name);
        final TextView itemCount = view.findViewById(R.id.item_count);
        TextView supplier = view.findViewById(R.id.supplier);
        TextView price = view.findViewById(R.id.price);

        int InventoryId = cursor.getInt(cursor.getColumnIndex(ID));
        String InventoryItemName = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
        String InventorySupplier = cursor.getString(cursor.getColumnIndex(SUPPLIER));
        int InventoryItemPrice = cursor.getInt(cursor.getColumnIndex(PRICE));
        int InventoryItemCount = cursor.getInt(cursor.getColumnIndex(COUNT));

        id.setText(String.valueOf(InventoryId));
        itemName.setText(InventoryItemName);
        supplier.setText(InventorySupplier);
        price.setText(String.valueOf(InventoryItemPrice));
        itemCount.setText(String.valueOf(InventoryItemCount));
        final Button sell = view.findViewById(R.id.sell);
        sell.setFocusable(false);

        final long inventoryId = cursor.getLong(cursor.getColumnIndex(ID));

        quantityMinusOne = cursor.getInt(cursor.getColumnIndex(COUNT));

        if (quantityMinusOne == 0) {
            sell.setEnabled(false);
        } else sell.setEnabled(true);

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityMinusOne = Integer.parseInt(itemCount.getText().toString()) - 1;

                itemCount.setText(String.valueOf(quantityMinusOne));

                ContentValues values = new ContentValues();
                values.put(COUNT, quantityMinusOne);

                String[] args = {String.valueOf(inventoryId)};

                context.getContentResolver().update(URI, values, ID + " = ? ", args);
            }
        });
    }
}
