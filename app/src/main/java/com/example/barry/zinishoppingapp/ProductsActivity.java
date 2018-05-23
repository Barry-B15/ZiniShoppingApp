package com.example.barry.zinishoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ProductsAdapter adapter;
    private List<Product> productList;

    // RETRIEVE DATA FROM FIRESTORE
    // 1st get an instance of the firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        adapter = new ProductsAdapter(this, productList);

        recyclerView.setAdapter(adapter);

        // FETCH ALL THE PRODUCTS IN THE COLLECTION
        db.collection("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        // hide the progress bar
                        progressBar.setVisibility(View.GONE);

                        // check that the snapshot is not empty
                        if (!queryDocumentSnapshots.isEmpty()) {

                            // create a list to store the snapshots
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            // we have this now in document snapshot format, we need to
                            // convert this document snapshot to product obj
                            // so we can display in our recycler view
                            for (DocumentSnapshot doc : list) {

                                Product p = doc.toObject(Product.class);

                                // then add the product to the product list
                                productList.add(p);
                            }

                            // then tell the recycler about data change
                            adapter.notifyDataSetChanged();
                            //RUN
                        }
                    }
                });
    }
}
