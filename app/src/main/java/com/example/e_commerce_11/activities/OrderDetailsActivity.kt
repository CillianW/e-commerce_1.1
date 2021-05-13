package com.example.e_commerce_11.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import kotlinx.android.synthetic.main.activity_confirm_details.*
import kotlinx.android.synthetic.main.activity_confirm_payment.*
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.toolbar_settings_activity

private lateinit var orderID: String
private lateinit var userDetails: User

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

        FireStoreClass().getUserDetails(this)

    }

    fun userDetailsSuccess(user: User){

        userDetails = user

        dismissProgressDialogue()

        text_customer_name.text = "${user.firstName} ${user.surname}"
        text_customer_email.text = "${user.email}"
        text_customer_phone.text = "${user.phoneNumber}"
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
}