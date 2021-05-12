package com.example.e_commerce_11.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.CartItem
import com.example.e_commerce_11.models.Order
import com.example.e_commerce_11.utilities.CartItemAdapter
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.OrderItemAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_confirm_payment.*
import kotlinx.android.synthetic.main.activity_order.*

private var orderID = String()

class OrderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        displayProgressDialogue("Please Wait")

        if (intent.hasExtra(Constants.ORDER_ID)) {
            orderID = intent.getStringExtra(Constants.ORDER_ID).toString()
            text_order_number_here.setText(orderID)
        }

        getOrderItems(FireStoreClass().getCurrentUserID())

        btn_return_to_dashboard.setOnClickListener{
            val intent = Intent(this@OrderActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getOrderItems(userID: String) {

        val items: ArrayList<Order> = ArrayList()

        FirebaseFirestore.getInstance().collection(Constants.ORDERS)
            .whereEqualTo("userID", userID)
            .whereEqualTo("orderID", orderID)
            //get request used to retrieve info
            .get()
            .addOnSuccessListener { result ->

                for (x in result) {

                    val item = Order()

                    item.orderItemID = x.getString(Constants.ORDER_ITEM_ID).toString()
                    item.userID = x.getString(Constants.USER_ID).toString()
                    item.orderItemName = x.getString(Constants.ORDER_ITEM_NAME).toString()
                    item.orderItemID = x.getString(Constants.ORDER_ITEM_NAME).toString()
                    item.orderItemPrice = x.getString(Constants.ORDER_ITEM_PRICE).toString()
                    item.orderItemQuantity = x.getString(Constants.ORDER_ITEM_QUANTITY).toString()

                    items.add(item)
                }

                calculateOrderTotal(items)

                // Set the LayoutManager that this RecyclerView will use.
                rv_Items_List_order_confirmation.layoutManager = LinearLayoutManager(this)
                // Adapter class is initialized and list is passed in the param.
                val orderItemAdapter = OrderItemAdapter(this, items)
                // adapter instance is set to the recyclerview to inflate the items.
                rv_Items_List_order_confirmation.adapter = orderItemAdapter

                dismissProgressDialogue()
            }
            .addOnFailureListener { e ->
                Log.e(e.toString(), "Error loading products")
                dismissProgressDialogue()
            }

    }

    fun calculateOrderTotal(items: ArrayList<Order>) {
        var cartTotal = 0

        for (i in items) {
            cartTotal = cartTotal + (i.orderItemPrice.toInt() * i.orderItemQuantity.toInt())
        }

        text_order_total_calculated.setText(cartTotal.toString())
    }

    override fun onBackPressed() {
        doubleBackPressToExit()
    }
}