package com.example.e_commerce_11.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.Constants.CART_TOTAL
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_confirm_details.*
import kotlinx.android.synthetic.main.activity_settings.*

private lateinit var userDetails: User
private var cartTotal = 0

class ConfirmDetailsActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_details)

        setupActionBar()
        displayProgressDialogue("Please Wait")
        FireStoreClass().getUserDetails(this)

        if (intent.hasExtra(CART_TOTAL)) {
            cartTotal = intent.getIntExtra(CART_TOTAL, 0)
            text_confirm_price.setText(cartTotal.toString())
        }

        btn_change_details.setOnClickListener(this)
        btn_proceed_to_payment.setOnClickListener(this)
    }

    fun userDetailsSuccess(user: User){

        userDetails = user

        dismissProgressDialogue()

        text_confirm_name.text = "${user.firstName} ${user.surname}"
        text_confirm_email.text = "${user.email}"
        text_confirm_phone.text = "${user.phoneNumber}"
        text_confirm_address1.text = "${user.address1}"
        text_confirm_address2.text = "${user.address2}"
        text_confirm_address3.text = "${user.address3}"
        text_confirm_address4.text = "${user.address4}"


    }

    override fun onClick(v: View?) {
        if (v != null) {

            when (v.id) {

                R.id.btn_change_details -> {
                    startActivity(Intent(this@ConfirmDetailsActivity, SettingsActivity::class.java))
                }

                R.id.btn_proceed_to_payment -> {
                    val intent = Intent(this@ConfirmDetailsActivity, ConfirmPaymentActivity::class.java)

                    intent.putExtra(Constants.CART_TOTAL, cartTotal)
                    startActivity(intent)
                }
            }
        }
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