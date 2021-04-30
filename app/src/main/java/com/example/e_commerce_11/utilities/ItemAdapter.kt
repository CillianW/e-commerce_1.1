package com.example.e_commerce_11.utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.ui.products.ProductsFragment
import com.example.e_commerce_11.models.Product
import kotlinx.android.synthetic.main.items_layout.view.*

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */
class ItemAdapter(val context: Context, val items: ArrayList<Product>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.items_layout,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)

        holder.LinearLayoutItem.text_item_name.setText(item.productName)
        holder.LinearLayoutItem.text_item_description.setText(item.productDescription)
        holder.LinearLayoutItem.text_item_price.setText("€"+item.price)
        holder.LinearLayoutItem.text_item_quantity.setText(item.quantity)
        GlideLoader(context).loadUserProfile(item.productImgURI, holder.LinearLayoutItem.img_item)
//        holder.LinearLayoutItem.img_item.setImageURI(item.productImgURI)
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val LinearLayoutItem = view
    }
}