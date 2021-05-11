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
import com.example.e_commerce_11.models.User
import kotlinx.android.synthetic.main.activity_cart.*
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
        cartItem[position].cartItemQuantity = item.cartItemQuantity

        holder.linearLayoutItem.btn_remove_from_cart.setOnClickListener {

            if(holder.linearLayoutItem.text_item_quantity.text.toString().toInt() > 1) {
                FireStoreClass().removeProductFromCart(
                    context,
                    cartItem[position],
                    FireStoreClass().getCurrentUserID()
                )

                cartItem[position].cartItemQuantity = (cartItem[position].cartItemQuantity.toInt() - 1).toString()

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

                cartItem.removeAt(position)
                items.removeAt(position)
                (context as CartActivity).calculateCartTotal(items)
                this.notifyItemRemoved(position)
                this.notifyItemRangeChanged(0, cartItem.size)
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

