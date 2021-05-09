package com.example.e_commerce_11.utilities

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.CartActivity
import com.example.e_commerce_11.activities.DashboardActivity
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.cart_item_layout.view.*
import kotlinx.android.synthetic.main.items_layout.view.*
import kotlinx.android.synthetic.main.items_layout.view.img_item
import kotlinx.android.synthetic.main.items_layout.view.text_item_name
import kotlinx.android.synthetic.main.items_layout.view.text_item_price
import kotlinx.android.synthetic.main.items_layout.view.text_item_quantity

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

private var cartItem: ArrayList<CartItem> = ArrayList()

class CartItemAdapter(val context: Context, var items: ArrayList<CartItem>) :
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
        holder.linearLayoutItem.text_item_price.setText("€" + item.cartItemPrice)
        holder.linearLayoutItem.text_item_quantity.setText(item.cartItemQuantity)
        GlideLoader(context).loadItem(item.cartItemImgURI, holder.linearLayoutItem.img_item)

        cartItem.add(CartItem())

        cartItem[position].cartItemID = item.cartItemID

        holder.linearLayoutItem.btn_remove_from_cart.setOnClickListener {

            if(holder.linearLayoutItem.text_item_quantity.text.toString().toInt() > 1) {
                FireStoreClass().removeProductFromCart(
                    context,
                    cartItem[position],
                    FireStoreClass().getCurrentUserID()
                )

                holder.linearLayoutItem.text_item_quantity
                    .setText(
                        (holder.linearLayoutItem.text_item_quantity.text.toString()
                            .toInt() - 1).toString()
                    )
            }
            else{
                FireStoreClass().removeProductFromCart(
                    context,
                    cartItem[position],
                    FireStoreClass().getCurrentUserID()
                )

                this.notifyItemRemoved(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val linearLayoutItem = view

    }

}

