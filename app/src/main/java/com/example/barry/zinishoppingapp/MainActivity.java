package com.example.barry.zinishoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Video 1: Save data to Firestore
 * https://youtu.be/fGiIUi3FqfQ
 *
 * 1. add firesore to project
 *          tools > firebase > Firestore
 *          >Read and Write doc to Cloud FireStore
 *          > Connect to Firebase
 *          > add Cloud Firestore
 *
 * 2. Create a Model class "Products" to save a product
 * 3. Here in main, create a new Product obj
 * Run
 *
 * Video 2 https://youtu.be/NWEfGZeDuAY
 *
 * Retrieve data from Firestore in a Recycler
 *
 * 1. create ProductsAdapter to handle recycler
 * 2. create a layout for the products "layout_products
 * 3. Create a new ProductsActivity
 * 4. Add a textview in main.xml for users to click to view product list
 *
 * 5. add recyclerView to product_activity
 * 6. add click listener to the textview in 4 above in main.java
 *
 * 7. In ProductsActivity class Retrieve the data from Firestore
 *
 * ---------------------------------------------------------------------
 * Add Index to use multi queries: In Firestore > Database > Index
 * > Add Index Manually > name Collection = products > OrderBy "Add Field"
 * > Create Index
 * -----------------------------------------------------------------------
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // create firestore obj
    FirebaseFirestore db;

    // define the widgets
    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;
    private EditText editTextQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init the db
        db = FirebaseFirestore.getInstance();

        // init the views
        editTextName = findViewById(R.id.edittext_name);
        editTextBrand = findViewById(R.id.edittext_brand);
        editTextDesc = findViewById(R.id.edittext_desc);
        editTextPrice = findViewById(R.id.edittext_price);
        editTextQty = findViewById(R.id.edittext_qty);

        // init the btn
        findViewById(R.id.button_save).setOnClickListener(this);

        //v2 add click listener to the textview
        findViewById(R.id.textview_view_products).setOnClickListener(this);
    }


    // validate the inputs
    private boolean hasValidationErrors(String name, String brand, String desc, String price, String qty) {

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return true;
        }

        if (brand.isEmpty()) {
            editTextBrand.setError("Brand required");
            editTextBrand.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            editTextDesc.setError("Description required");
            editTextDesc.requestFocus();
            return true;
        }

        if (price.isEmpty()) {
            editTextPrice.setError("Price required");
            editTextPrice.requestFocus();
            return true;
        }

        if (qty.isEmpty()) {
            editTextQty.setError("Quantity required");
            editTextQty.requestFocus();
            return true;
        }

        return false;
    }

    // add click listener to the btn
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_save:
                saveProduct();
                break;

            case R.id.textview_view_products:
                startActivity(new Intent(this, ProductsActivity.class));
                break;
        }
    }

    private void saveProduct() {

        String name = editTextName.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        String qty = editTextQty.getText().toString().trim();

        if (!hasValidationErrors(name, brand, desc, price, qty)) {

            // create a firestore collection ref
            CollectionReference dbProducts = db.collection("products");
            // all products will be saved in the collection called "products"

            // create a new Product obj
            Product product = new Product(
                    name,
                    brand,
                    desc,
                    Double.parseDouble(price),
                    Integer.parseInt(qty)
            );
            // this started giving error after I added timestamp in Product.java
            // FIX: alt+enter and change signature to use Date time

            // SAVE PRODUCT TO FIRESTORE
            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(MainActivity.this, "Product Added!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MainActivity.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

}
