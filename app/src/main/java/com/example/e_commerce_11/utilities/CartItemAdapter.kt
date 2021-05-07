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

class CartItemAdapter(val context: Context, val items: ArrayList<CartItem>) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.cart_item_layout,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.linearLayoutItem.text_item_name.setText(item.cartItemName)
//        holder.linearLayoutItem.text_item_description.setText(item.cartItemDescription)
        holder.linearLayoutItem.text_item_price.setText("€" + item.cartItemPrice)
        holder.linearLayoutItem.text_item_quantity.setText("Quantity: " + item.cartItemQuantity)
        GlideLoader(context).loadItem(item.cartItemImgURI, holder.linearLayoutItem.img_item)

        cartItem.add(CartItem())

        cartItem[position].cartItemID = item.cartItemID

        holder.linearLayoutItem.btn_add_to_cart.setOnClickListener {

            FireStoreClass().addProductToCart(context, cartItem[position])
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val linearLayoutItem = view

    }
}

