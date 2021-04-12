package com.example.e_commerce_11.activities

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.os.Bundle
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.GlideLoader
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupActionBar()

        getUserDetails()
    }

    //this function sets up the back button at the of the screen
    private fun setupActionBar(){
        setSupportActionBar(toolbar_settings_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        toolbar_settings_activity.setNavigationOnClickListener{
            onBackPressed()
            finish()}
    }

    private fun getUserDetails(){
        displayProgressDialogue(resources.getString(R.string.please_wait))
        FireStoreClass().getUserDetails(this)
    }

    fun userDetailsSuccess(user: User){
        dismissProgressDialogue()

        GlideLoader(this).loadUserProfile(user.imageURL, settings_profile_pic)
        settings_user_name.text = "${user.firstName} ${user.surname}"
        settings_user_email.text = "${user.email}"
        settings_user_phone_number.text = "${user.phoneNumber}"
        settings_user_gender.text = "${user.gender}"
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }
}