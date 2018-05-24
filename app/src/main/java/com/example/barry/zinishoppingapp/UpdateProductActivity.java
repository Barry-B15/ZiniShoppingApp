package com.example.barry.zinishoppingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Update Products
 * https://youtu.be/VHLj-ndsZnY
 * 1. declare the views and init them
 * 2. create a validation method for the views
 * 3. create the update product method
 * 4. Add a click listener to the update button and call updaateproduct in it so that
 *      when user click on it, it will open the product for update
 * 5. Go to the adapter to add click listener to the items
 *      - add an Intent in the click listener
 * 6. Go to Product class implement Serializable
 * 7. Come back to UpdateProductActivity to get the intent / get the item that was clicked
 * 8. use the product to display the values
 * 9. we need to product id to display it, create "id" in Product.java
 *      use @Exclude on it as we will not save it as a product
 *      remember to get the getter and setter
 * 10. Go to the ProductsActivity() class db.collection.get() to set the id
 * 11. To update we have to override the old product
 */
public class UpdateProductActivity extends AppCompatActivity implements View.OnClickListener {

    // define the widgets
    private EditText editTextName;
    private EditText editTextBrand;
    private EditText editTextDesc;
    private EditText editTextPrice;
    private EditText editTextQty;

    private Product product;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        //7. UUUUUU get the Intent we created in the ProductAdapter() class
        product = (Product) getIntent().getSerializableExtra("product"); // name must be the same as we gave in the adapter
        db = FirebaseFirestore.getInstance();
        // init the views
        editTextName = findViewById(R.id.edittext_name);
        editTextBrand = findViewById(R.id.edittext_brand);
        editTextDesc = findViewById(R.id.edittext_desc);
        editTextPrice = findViewById(R.id.edittext_price);
        editTextQty = findViewById(R.id.edittext_qty);

        //8. Display the values
        editTextName.setText(product.getName());
        editTextBrand.setText(product.getBrand());
        editTextDesc.setText(product.getDescription());
        editTextPrice.setText(String.valueOf(product.getPrice()));
        editTextQty.setText(String.valueOf(product.getQty()));

        // init the btn
        findViewById(R.id.button_update).setOnClickListener(this);

        //v2 add click listener to the textview
        //findViewById(R.id.textview_view_products).setOnClickListener(this);
    }

    // validate the inputs
    private boolean hasValidationErrors(String name,
                                        String brand,
                                        String desc,
                                        String price,
                                        String qty) {

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

    private void updateProduct() {

        String name = editTextName.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        String qty = editTextQty.getText().toString().trim();

        if (!hasValidationErrors(name, brand, desc, price, qty)) {

            // create the new product to save
            Product p = new Product(
                    name, brand, desc,
                    Double.parseDouble(price),
                    Integer.parseInt(qty)
            );

            //11. update the product
            db.collection("products") // collection: products
                    .document(product.getId()) // document is the one we set in ProductActivity
                    .set(p) // pass a new product(created above) to save
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateProductActivity.this,
                                    "Product updated", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_update:
                updateProduct();
                break;
        }
    }
}
