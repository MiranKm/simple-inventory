package com.example.miran3ma.invetoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.COUNT;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.IMAGE;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.ITEM_NAME;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.PRICE;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.SUPPLIER;
import static com.example.miran3ma.invetoryapp.DataBase.InventoryContract.ItemEntry.URI;

public class AddActivity extends AppCompatActivity {

    EditText ItemName;
    EditText ItemSupplier;
    EditText ItemPrice;
    EditText ItemQuantity;
    Button addButton;
    String path;
    final static int PICK_IMAGE = 01101101; //Binary  of M

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ItemName = findViewById(R.id.item_name);
        ItemSupplier = findViewById(R.id.item_supplier);
        ItemPrice = findViewById(R.id.item_price);
        ItemQuantity = findViewById(R.id.item_quantity);
        addButton = findViewById(R.id.addbtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!ItemName.getText().toString().trim().isEmpty() && !ItemPrice.getText().toString().trim().isEmpty() &&
                        !ItemSupplier.getText().toString().trim().isEmpty() && !ItemQuantity.getText().toString().trim().isEmpty() && path != null) {
                    ContentValues values = new ContentValues();
                    values.put(ITEM_NAME, ItemName.getText().toString());
                    values.put(SUPPLIER, ItemSupplier.getText().toString());
                    values.put(PRICE, ItemPrice.getText().toString());
                    values.put(COUNT, ItemQuantity.getText().toString());
                    values.put(IMAGE, String.valueOf(path));

                    getContentResolver()
                            .insert(URI, values);

                    finish();
                } else
                    Toast.makeText(AddActivity.this, "the fields can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_image, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {

                Toast.makeText(AddActivity.this, "didnt pic anything", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {

                File file = imagesFiles.get(0);
                path = file.getAbsolutePath();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itemAdd) {
        switch (itemAdd.getItemId()) {
            case R.id.addimage:
                EasyImage.openGallery(AddActivity.this, PICK_IMAGE);
                break;
        }
        return super.onOptionsItemSelected(itemAdd);
    }
}
