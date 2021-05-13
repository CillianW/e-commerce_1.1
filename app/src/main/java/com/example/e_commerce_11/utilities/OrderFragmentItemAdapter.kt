package com.example.e_commerce_11.utilities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.ConfirmPaymentActivity
import com.example.e_commerce_11.activities.OrderDetailsActivity
import kotlinx.android.synthetic.main.order_fragment_item_layout.view.*

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

class OrderFragmentItemAdapter (val context: Context, var items: ArrayList<String>) :
    RecyclerView.Adapter<OrderFragmentItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(
            R.layout.order_fragment_item_layout,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.linearLayoutItem.text_order_number.setText(item)
        holder.linearLayoutItem.text_order.setText("Order " + (position+1) + ":")

        holder.linearLayoutItem.btn_view_order.setOnClickListener {
            val intent = Intent(this.context, OrderDetailsActivity::class.java)

            intent.putExtra(Constants.ORDER_ID, item)
            startActivity(context, intent, Bundle())
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val linearLayoutItem = view

    }

}