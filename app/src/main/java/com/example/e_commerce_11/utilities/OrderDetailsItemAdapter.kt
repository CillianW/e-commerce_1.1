package com.example.e_commerce_11.utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.CartItem
import com.example.e_commerce_11.models.Order
import com.example.e_commerce_11.models.Product
import com.example.e_commerce_11.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.items_layout.view.*
import kotlinx.android.synthetic.main.order_item_layout.view.*

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */
private var cartItem: ArrayList<Order> = ArrayList()
private var userDetails = User()

class OrderDetailsItemAdapter(val context: Context, val items: ArrayList<Order>) :
    RecyclerView.Adapter<OrderDetailsItemAdapter.ViewHolder>() {

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

        holder.linearLayoutItem.text_order_item_name.setText(item.orderItemName)
        holder.linearLayoutItem.text_order_item_quantity.setText(item.orderItemQuantity)
        holder.linearLayoutItem.text_order_item_price.setText(item.orderItemPrice)

//        cartItem.add(Order())

//        cartItem[position].cartItemID = item.productID
//        cartItem[position].userID = FireStoreClass().getCurrentUserID()
//        cartItem[position].cartItemName = item.productName
//        cartItem[position].cartItemDescription = item.productDescription
//        cartItem[position].cartItemImgURI = item.productImgURI
//        cartItem[position].cartItemPrice = item.price


    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linearLayoutItem = view
    }

}

