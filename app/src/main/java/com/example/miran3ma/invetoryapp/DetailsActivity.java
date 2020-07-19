package com.example.miran3ma.invetoryapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.COUNT;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.ID;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.IMAGE;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.ITEM_NAME;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.SUPPLIER;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.URI;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Button delete;
    Button order;
    Button minus;
    Button plus;
    Long EditId;
    ImageView image;
    EditText amountQuantity;
    TextView name;
    TextView counPlusMinus;
    TextView supplier;
    TextView count;
    int itemCount;
    int quantityMinusOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.name);
        image = findViewById(R.id.image);
        supplier = findViewById(R.id.supplier);
        count = findViewById(R.id.count);
        delete = findViewById(R.id.delete);
        order = findViewById(R.id.order);
        minus = findViewById(R.id.minus_count);
        counPlusMinus = findViewById(R.id.count_plus_minus);
        plus = findViewById(R.id.plus_count);
        amountQuantity = findViewById(R.id.amout_quantity);


        EditId = getIntent().getLongExtra("ID", -1);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantityMinusOne = Integer.parseInt(count.getText().toString()) + 1;

                count.setText(String.valueOf(quantityMinusOne));

                ContentValues values = new ContentValues();
                values.put(COUNT, quantityMinusOne);

                String[] args = {String.valueOf(EditId)};

                DetailsActivity.this.getContentResolver().update(URI, values, ID + " = ? ", args);

            }
        });


        minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                quantityMinusOne = Integer.parseInt(count.getText().toString()) - 1;

                count.setText(String.valueOf(quantityMinusOne));

                ContentValues values = new ContentValues();
                values.put(COUNT, quantityMinusOne);

                String[] args = {String.valueOf(EditId)};

                DetailsActivity.this.getContentResolver().update(URI, values, ID + " = ? ", args);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!amountQuantity.getText().toString().trim().isEmpty()) {

                    String[] args = {String.valueOf(EditId)};

                    ContentValues values = new ContentValues();
                    values.put(COUNT, Integer.parseInt(amountQuantity.getText().toString().trim()) + itemCount);

                    getContentResolver()
                            .update(URI, values, ID + " = ?", args);

                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("plain/text");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"miran9191@gmail.com"});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "this amount of this item is needed " + name.getText().toString()+ " and this amount "+ amountQuantity.getText().toString());
                    Intent intent = Intent.createChooser(sendIntent, null);
                    startActivity(intent);
                } else
                    Toast.makeText(DetailsActivity.this, "Please, Add a amount", Toast.LENGTH_SHORT).show();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(DetailsActivity.this).setTitle("are you sure ?").setMessage("this item is going to be deleted permanently").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String[] args = {String.valueOf(EditId)};

                        getContentResolver().delete(URI, ID + " = ?", args);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(URI, String.valueOf(EditId));
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToNext()) {
            String itemName = data.getString(data.getColumnIndex(ITEM_NAME));
            String itemSupplier = data.getString(data.getColumnIndex(SUPPLIER));
            itemCount = data.getInt(data.getColumnIndex(COUNT));

            if (itemCount == 0) {
                minus.setEnabled(false);
            } else
                minus.setEnabled(true);

            Glide.with(DetailsActivity.this).load(new File(data.getString(data.getColumnIndex(IMAGE)))).into(image);

            name.setText(itemName);
            supplier.setText(itemSupplier);
            count.setText(String.valueOf(itemCount));
            counPlusMinus.setText(String.valueOf(itemCount));
            Picasso.with(this).load(data.getString(data.getColumnIndex(IMAGE))).into(image);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
