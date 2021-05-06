package com.example.e_commerce_11.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.e_commerce_11.R
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import kotlinx.android.synthetic.main.activity_user_profile.*

class CartActivity : BaseActivity() {
    private lateinit var userDetails: User
    private var totalPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        displayProgressDialogue(R.string.please_wait.toString())

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }
    }





}
