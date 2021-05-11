package com.example.e_commerce_11.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.CartItem
import com.example.e_commerce_11.utilities.CartItemAdapter
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.OrderItemAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        displayProgressDialogue("Please Wait")

        setupActionBar()

        getCartItems(FireStoreClass().getCurrentUserID())
    }

    private fun getCartItems(userID: String) {

        val items: ArrayList<CartItem> = ArrayList()

        FirebaseFirestore.getInstance().collection(Constants.CARTS)
            .whereEqualTo("userID", userID)
            //get request used to retrieve info
            .get()
            .addOnSuccessListener { result ->

                for (x in result) {

                    val item = CartItem("", "", "", "", "", "")

                    item.cartItemID = x.getString(Constants.CART_ITEM_ID).toString()
                    item.userID = x.getString(Constants.USER_ID).toString()
                    item.cartItemName = x.getString(Constants.CART_ITEM_NAME).toString()
                    item.cartItemDescription = x.getString(Constants.CART_ITEM_DESCRIPTION).toString()
                    item.cartItemImgURI = x.getString(Constants.CART_ITEM_URI).toString()
                    item.cartItemPrice = x.getString(Constants.CART_ITEM_PRICE).toString()
                    item.cartItemQuantity = x.getString(Constants.CART_ITEM_QUANTITY).toString()

                    items.add(item)
                }

                calculateOrderTotal(items)

                // Set the LayoutManager that this RecyclerView will use.
                rv_Items_List_order_confirmation.layoutManager = LinearLayoutManager(this)
                // Adapter class is initialized and list is passed in the param.
                val orderItemAdapter = this?.let { OrderItemAdapter(it, items) }
                // adapter instance is set to the recyclerview to inflate the items.
                rv_Items_List_order_confirmation.adapter = orderItemAdapter

                dismissProgressDialogue()
            }
            .addOnFailureListener { e ->
                Log.e(e.toString(), "Error loading products")
                dismissProgressDialogue()
            }

    }

    fun calculateOrderTotal(items: ArrayList<CartItem>) {
        var cartTotal = 0

        for (i in items) {
            cartTotal = cartTotal + (i.cartItemPrice.toInt() * i.cartItemQuantity.toInt())
        }

        text_order_total_calculated.setText(cartTotal.toString())
    }

    //this function sets up the back button at the of the screen
    private fun setupActionBar() {
        setSupportActionBar(toolbar_confirm_details)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        toolbar_confirm_details.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }
}