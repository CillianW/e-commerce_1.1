package com.example.e_commerce_11.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_commerce_11.R
import com.example.e_commerce_11.utilities.Constants
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_confirm_details.*
import kotlinx.android.synthetic.main.activity_confirm_details.text_confirm_price
import kotlinx.android.synthetic.main.activity_confirm_payment.*

private var cartTotal = 0

class ConfirmPaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)

        if (intent.hasExtra(Constants.CART_TOTAL)) {
            cartTotal = intent.getIntExtra(Constants.CART_TOTAL, 0)
            total_order_cost_amount.setText(cartTotal.toString())
        }

        setupActionBar()

        btn_confirm_payment.setOnClickListener{


            val intent = Intent(this@ConfirmPaymentActivity, OrderActivity::class.java)

            intent.putExtra(Constants.CART_TOTAL, cartTotal)
            startActivity(intent)
        }
    }

    private fun generateOrder(){

    }

    //this function sets up the back button at the of the screen
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
}