package com.example.e_commerce_11.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_11.R
import com.example.e_commerce_11.models.CartItem
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.CartItemAdapter
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.ItemAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.cart_item_layout.*
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.fragment_products.rv_Items_List_products

class CartActivity : BaseActivity() {
    private lateinit var userDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setupActionBar()

        displayProgressDialogue(R.string.please_wait.toString())

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!

            val userID = userDetails.id

            getCartItems(userID)
        }

        btn_purchase.setOnClickListener {
            if(text_cart_calculated_price.text == "0"){
                Toast.makeText(this, "You have no items in your cart", Toast.LENGTH_SHORT).show()
            }
            else{

            }
        }

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
                    item.cartItemDescription =
                        x.getString(Constants.CART_ITEM_DESCRIPTION).toString()
                    item.cartItemImgURI = x.getString(Constants.CART_ITEM_URI).toString()
                    item.cartItemPrice = x.getString(Constants.CART_ITEM_PRICE).toString()
                    item.cartItemQuantity = x.getString(Constants.CART_ITEM_QUANTITY).toString()

                    items.add(item)
                }

                calculateCartTotal(items)

                // Set the LayoutManager that this RecyclerView will use.
                rv_Items_List_cart.layoutManager = LinearLayoutManager(this)
                // Adapter class is initialized and list is passed in the param.
                val itemAdapter = this?.let { CartItemAdapter(it, items) }
                // adapter instance is set to the recyclerview to inflate the items.
                rv_Items_List_cart.adapter = itemAdapter

                dismissProgressDialogue()
            }
            .addOnFailureListener { e ->
                Log.e(e.toString(), "Error loading products")
                dismissProgressDialogue()
            }

    }

    fun calculateCartTotal(items: ArrayList<CartItem>) {
        var cartTotal = 0

        for (i in items) {
            cartTotal = cartTotal + (i.cartItemPrice.toInt() * i.cartItemQuantity.toInt())
        }

        text_cart_calculated_price.setText(cartTotal.toString())
    }

    //this function sets up the back button at the of the screen
    private fun setupActionBar() {
        setSupportActionBar(toolbar_cart_activity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        toolbar_cart_activity.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }
}
