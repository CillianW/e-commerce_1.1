package com.example.e_commerce_11.activities

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.GlideLoader
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class SettingsActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupActionBar()

        //getUserDetails()
        btn_edit_details.setOnClickListener(this)
        btn_addresses.setOnClickListener(this)
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


        //GlideLoader(this).loadUserProfile(user.imageURL, settings_profile_pic)
        Glide.with(this).load("${user.imageURL}")
            .placeholder(R.drawable.empty_profile_pic).into(settings_profile_pic)
        settings_user_name.text = "${user.firstName} ${user.surname}"
        settings_user_email.text = "${user.email}"
        settings_user_phone_number.text = "${user.phoneNumber}"
        settings_user_gender.text = "${user.gender}"
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    override fun onClick(v: View?) {
        if (v != null) {

            when (v.id) {

                R.id.btn_edit_details -> {
                    val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)

                    //we can send parcelized objects to the next activity using the putExtra() function
                    //intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
                    startActivity(intent)                }

                R.id.btn_addresses -> {

                }
            }
        }
    }
}