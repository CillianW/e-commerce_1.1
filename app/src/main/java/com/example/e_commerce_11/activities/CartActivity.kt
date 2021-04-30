package com.example.e_commerce_11.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce_11.R
import com.example.e_commerce_11.models.User

class CartActivity : BaseActivity() {
    private lateinit var userDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        displayProgressDialogue(R.string.please_wait.toString())

    }

}