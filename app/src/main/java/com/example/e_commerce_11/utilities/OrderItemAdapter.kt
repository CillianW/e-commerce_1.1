package com.example.e_commerce_11.utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.CartActivity
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.CartItem
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.cart_item_layout.view.*
import kotlinx.android.synthetic.main.items_layout.view.*
import kotlinx.android.synthetic.main.items_layout.view.img_item
import kotlinx.android.synthetic.main.items_layout.view.text_item_name
import kotlinx.android.synthetic.main.items_layout.view.text_item_price
import kotlinx.android.synthetic.main.items_layout.view.text_item_quantity
import kotlinx.android.synthetic.main.order_item_layout.view.*


/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

private var orderItems: ArrayList<CartItem> = ArrayList()

class OrderItemAdapter(val context: Context, var items: ArrayList<CartItem>) :
    RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.order_item_layout,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.linearLayoutItem.text_order_item_name.setText(item.cartItemName)
        holder.linearLayoutItem.text_order_item_quantity.setText(item.cartItemQuantity)
        holder.linearLayoutItem.text_order_item_price.setText("€" + item.cartItemPrice)

        orderItems.add(CartItem())

        orderItems[position].cartItemID = item.cartItemID
        orderItems[position].cartItemQuantity = item.cartItemQuantity

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val linearLayoutItem = view

    }
}