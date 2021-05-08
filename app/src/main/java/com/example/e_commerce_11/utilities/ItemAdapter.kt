package com.example.e_commerce_11.utilities

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.CartItem
import com.example.e_commerce_11.models.Product
import kotlinx.android.synthetic.main.items_layout.view.*

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

private var cartItem: ArrayList<CartItem> = ArrayList()

class ItemAdapter(val context: Context, val items: ArrayList<Product>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.items_layout,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.linearLayoutItem.text_item_name.setText(item.productName)
        holder.linearLayoutItem.text_item_description.setText(item.productDescription)
        holder.linearLayoutItem.text_item_price.setText("€" + item.price)
        holder.linearLayoutItem.text_item_quantity.setText("Available: " + item.quantity)
        GlideLoader(context).loadItem(item.productImgURI, holder.linearLayoutItem.img_item)

        cartItem.add(CartItem())

        cartItem[position].cartItemID = item.productID
        cartItem[position].userID = FireStoreClass().getCurrentUserID()
        cartItem[position].cartItemName = item.productName
        cartItem[position].cartItemDescription = item.productDescription
        cartItem[position].cartItemImgURI = item.productImgURI
        cartItem[position].cartItemPrice = item.price

        holder.linearLayoutItem.btn_add_to_cart.setOnClickListener {

            FireStoreClass().addProductToCart(context, FireStoreClass().getCurrentUserID(), cartItem[position])
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val linearLayoutItem = view

    }
}

