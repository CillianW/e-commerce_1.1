package com.example.e_commerce_11.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.Order
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.OrderDetailsItemAdapter
import com.example.e_commerce_11.utilities.OrderItemAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_confirm_details.*
import kotlinx.android.synthetic.main.activity_confirm_payment.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.activity_order_details.text_order_number
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.toolbar_settings_activity

private lateinit var orderID: String
private lateinit var userID: String

class OrderDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        setupActionBar()

        displayProgressDialogue("Please Wait")

        if (intent.hasExtra(Constants.ORDER_ID)) {
            orderID = intent.getStringExtra(Constants.ORDER_ID).toString()
            text_order_number.setText(orderID)
        }

        getOrder(orderID)

    }

    fun userDetailsSuccess(user: User){

//        userDetails = user

        dismissProgressDialogue()

        text_customer_name.text = "${user.firstName} ${user.surname}"
        text_customer_email.text = "${user.email}"
        text_customer_address1.text = "${user.address1}"
        text_customer_address2.text = "${user.address2}"
        text_customer_address3.text = "${user.address3}"
        text_customer_address4.text = "${user.address4}"


    }

    //this function sets up the back button at the of the screen
    private fun setupActionBar(){
        setSupportActionBar(toolbar_order_details)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        toolbar_order_details.setNavigationOnClickListener{
            onBackPressed()
            finish()}
    }

    private fun getOrder(orderID: String){

        var orderItems: ArrayList<Order> = ArrayList()

        FirebaseFirestore.getInstance().collection(Constants.ORDERS)
            .whereEqualTo(Constants.ORDER_ID, orderID)
            .get()
            .addOnSuccessListener { result ->

                for(x in result){

                    val orderItem = Order()

                    orderItem.orderItemName = x.getString(Constants.ORDER_ITEM_NAME).toString()
                    orderItem.orderItemQuantity = x.getString(Constants.ORDER_ITEM_QUANTITY).toString()
                    orderItem.orderItemPrice = x.getString(Constants.ORDER_ITEM_PRICE).toString()
                    userID = x.getString(Constants.USER_ID).toString()

                    orderItems.add(orderItem)
                }


                getOrderUserDetails(userID)

                calculateOrderTotal(orderItems)


                // Set the LayoutManager that this RecyclerView will use.
                rv_Items_List_order_details.layoutManager = LinearLayoutManager(this)
                // Adapter class is initialized and list is passed in the param.
                val orderItemDetailsAdapter = OrderDetailsItemAdapter(this, orderItems)
                // adapter instance is set to the recyclerview to inflate the items.
                rv_Items_List_order_details.adapter = orderItemDetailsAdapter

            }

    }

    fun calculateOrderTotal(items: ArrayList<Order>) {
        var orderTotal = 0

        for (i in items) {
            orderTotal = orderTotal + (i.orderItemPrice.toInt() * i.orderItemQuantity.toInt())
        }

        text_order_details_total_calculated.setText(orderTotal.toString())
    }

    fun getOrderUserDetails(userID: String){

        FirebaseFirestore.getInstance().collection(Constants.USERS)
            .whereEqualTo("id", userID)
            .get()
            .addOnSuccessListener { result ->
                val user = User()

                for(x in result) {
                    user.firstName = x.getString("firstName").toString()
                    user.surname = x.getString("surname").toString()
                    user.email = x.getString("email").toString()
                    user.address1 = x.getString(Constants.ADDRESS_1).toString()
                    user.address2 = x.getString(Constants.ADDRESS_2).toString()
                    user.address3 = x.getString(Constants.ADDRESS_3).toString()
                    user.address4 = x.getString(Constants.ADDRESS_4).toString()

                    userDetailsSuccess(user)
                }
            }
    }
}