package com.example.e_commerce_11.utilities

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */
object Constants {

    //constants that are used throughout the program
    const val USERS: String = "users"
    const val APP_PREFERENCES = "AppPreferences"
    const val LOGGED_IN_USERNAME = "LoggedInUserName"
    const val EXTRA_USER_DETAILS = "ExtraUserDetails"
    const val GALLERY_PERMISSION_CODE = 1
    const val IMAGE_REQUEST_CODE = 2
    const val MALE : String = "male"
    const val FEMALE : String = "female"
    const val GENDER : String = "gender"
    const val PHONE_NUMBER : String = "phoneNumber"
    const val IMAGE_URL : String = "imageURL"


    //run the phone's image selector function
    fun imageSelector(activity: Activity){
        //create intent to launch media selection on device
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        activity.startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE)
    }
}