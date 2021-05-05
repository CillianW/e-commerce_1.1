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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.items_layout.view.*
import kotlin.coroutines.coroutineContext

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

private lateinit var cartItem: CartItem

class ItemAdapter (val context: Context, val items: ArrayList<Product>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view : View = LayoutInflater.from(context).inflate(
                R.layout.items_layout,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.linearLayoutItem.btn_add_to_cart.setOnClickListener(this)

        holder.linearLayoutItem.text_item_name.setText(item.productName)
        holder.linearLayoutItem.text_item_description.setText(item.productDescription)
        holder.linearLayoutItem.text_item_price.setText("€" + item.price)
        holder.linearLayoutItem.text_item_quantity.setText("Available: " + item.quantity)
        GlideLoader(context).loadItem(item.productImgURI, holder.linearLayoutItem.img_item)

        holder.linearLayoutItem.setOnClickListener(this)

        cartItem = CartItem()

        cartItem.cartItemID = item.productID
        cartItem.userID = FireStoreClass().getCurrentUserID()
        cartItem.cartItemName = item.productName
        cartItem.cartItemDescription = item.productDescription
        cartItem.cartItemImgURI = item.productImgURI
        cartItem.cartItemPrice = item.price

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val linearLayoutItem = view

    }

    override fun onClick(v: View?) {
        if(v != null){

            when (v.id){

                R.id.btn_add_to_cart -> {

                    if(cartItem.cartItemQuantity == ""){
                        cartItem.cartItemQuantity = "1"
                    }
                    else{
                        cartItem.cartItemQuantity = (cartItem.cartItemQuantity.toInt() + 1).toString()
                    }
                    FireStoreClass().addProductToCart(cartItem)

                }
            }
        }
    }
}

