package com.example.barry.zinishoppingapp;

import android.content.Context;
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {

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
        }
    }
}
