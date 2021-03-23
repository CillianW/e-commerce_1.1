package com.example.e_commerce_11.activities

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.ContextCompat
import com.example.e_commerce_11.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*

//this activity provides the functionality to:
//      -display an error/success message in other activities
//      -validate a user entry
open class BaseActivity : AppCompatActivity() {

    //a snackBar is used to display pop up messages
    //this function sets the colour of the snackBar
    fun displaySnackBar(message: String, error: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, 2000)
        val snackBarView = snackBar.view

        if(error){
            snackBarView.setBackgroundColor((ContextCompat.getColor(this@BaseActivity,R.color.snackBar_colour_fail)))
        }
        else{
            snackBarView.setBackgroundColor((ContextCompat.getColor(this@BaseActivity,R.color.snackBar_colour_success)))
        }
        snackBar.show()
    }

}