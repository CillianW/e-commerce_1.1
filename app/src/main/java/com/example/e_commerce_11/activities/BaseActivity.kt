package com.example.e_commerce_11.activities

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.e_commerce_11.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_settings.*

//this activity provides the functionality to:
//      -display an error/success message in other activities
//      -validate a user entry
open class BaseActivity : AppCompatActivity() {

    private var backPressedOnce = false
    private lateinit var mProgressDialog: Dialog

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

    //this function is used to show the progress dialogue box
    fun displayProgressDialogue(text: String){
        mProgressDialog = Dialog(this)

        //set the content of the progress dialogue from a resource file
        mProgressDialog.setContentView(R.layout.dialogue_progress)

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //show the dialogue
        mProgressDialog.show()
    }

    fun dismissProgressDialogue(){
        mProgressDialog.dismiss()
    }

    fun doubleBackPressToExit(){

        if (this.backPressedOnce){
            super.onBackPressed()
            return
        }

        this.backPressedOnce = true

        Toast.makeText(this, resources.getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(
            {
                backPressedOnce = false
            },
            1500)
    }


}