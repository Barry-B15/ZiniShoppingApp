package com.example.barry.zinishoppingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    // declare the widgets
    private Context mCtx;
    private List<Product> productList;

    // create a constructor
    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        return new ProductViewHolder(LayoutInflater
                .from(mCtx)
                .inflate(R.layout.layout_products, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        // define the product list
        Product product = productList.get(position);

        //set the views to the view holder
        holder.textViewName.setText(product.getName());
        holder.textViewBrand.setText(product.getBrand());
        holder.textViewDesc.setText(product.getDescription());
        holder.textViewPrice.setText("Y" + product.getPrice());
        holder.textViewQty.setText("Available Units: " + product.getQty());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    //2. UUUU Update Product, implements onClicklistener UUUUUUU
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // declare the views
        TextView textViewName, textViewBrand, textViewDesc,textViewPrice, textViewQty;

        public ProductViewHolder(View itemView) {
            super(itemView);

            // init the views
            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);
            textViewDesc = itemView.findViewById(R.id.textview_desc);
            textViewPrice = itemView.findViewById(R.id.textview_price);
            textViewQty = itemView.findViewById(R.id.textview_qty);

            //1. UUUUUUUUUUUU video 3 Update Products UUUUUUUUUUUUU
            // Add click listener to the items
            itemView.setOnClickListener(this);
        }

        //3. UUUUUUUUUUUU auto created when 2 was implemented UUUUUUUU.
        // this method will be called whenever users click on any item in the recycler
        @Override
        public void onClick(View view) {

            Product product = productList.get(getAdapterPosition()); // get the product from the productList
            Intent intent = new Intent(mCtx, UpdateProductActivity.class); // go to UpdateProduct class
            intent.putExtra("product", product);
            // Fix error, To pass an obj to intent, we have to implement serializable interface
            // in the product class. Go to Product class an do that

            mCtx.startActivity(intent); // start the activity with the context mCtx
            // go to UpdateProductActivity to get the intent
        }
    }
}
