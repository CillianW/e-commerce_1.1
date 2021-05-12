package com.example.e_commerce_11.activities

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import androidx.core.content.IntentCompat
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.Order
import com.example.e_commerce_11.utilities.Constants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_confirm_details.*
import kotlinx.android.synthetic.main.activity_confirm_payment.*


private var cartTotal = 0
private var orderItems = ArrayList<Order>()

class ConfirmPaymentActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)

        if (intent.hasExtra(Constants.CART_TOTAL)) {
            cartTotal = intent.getIntExtra(Constants.CART_TOTAL, 0)
            total_order_cost_amount.setText(cartTotal.toString())
        }

        setupActionBar()
        getCartItems(FireStoreClass().getCurrentUserID())

        btn_confirm_payment.setOnClickListener {

            val orderID = FireStoreClass().generateOrder(orderItems, this)

            val intent = Intent(this@ConfirmPaymentActivity, OrderActivity::class.java)
            intent.putExtra(Constants.ORDER_ID, orderID)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    //this function sets up the back button at the top of the screen
    private fun setupActionBar() {
        setSupportActionBar(toolbar_confirm_Payment)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        toolbar_confirm_Payment.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun getCartItems(userID: String) {

        orderItems = ArrayList()

        FirebaseFirestore.getInstance().collection(Constants.CARTS)
            .whereEqualTo("userID", userID)
            //get request used to retrieve info
            .get()
            .addOnSuccessListener { result ->

                for (x in result) {

                    val order = Order()

                    order.userID = x.getString(Constants.USER_ID).toString()
                    order.orderItemQuantity = x.getString(Constants.CART_ITEM_QUANTITY).toString()
                    order.orderItemPrice = x.getString(Constants.CART_ITEM_PRICE).toString()
                    order.orderItemName = x.getString(Constants.CART_ITEM_NAME).toString()
                    order.orderItemID = x.getString(Constants.CART_ITEM_ID).toString()

                    orderItems.add(order)
                }
            }
    }
}