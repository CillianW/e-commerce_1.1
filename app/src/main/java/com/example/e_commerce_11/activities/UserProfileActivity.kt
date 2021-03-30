package com.example.e_commerce_11.activities

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.e_commerce_11.R
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //create a new User object and assign any parcelized user objects from the intent to it
        var userDetails = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        //disable editing and assign the appropriate user details to the relevant fields
        et_change_first_name.isEnabled = false
        et_change_first_name.setText(userDetails.firstName)

        et_change_surname.isEnabled = false
        et_change_surname.setText(userDetails.surname)

        et_change_emailID.isEnabled = false
        et_change_emailID.setText(userDetails.email)

        img_profile_pic.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (v != null) {

            when (v.id) {

                R.id.img_profile_pic -> {

                    //on pressing the img_profile_pic button, check if permission to access camera is granted.
                    //if not, request permission
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.imageSelector(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.GALLERY_PERMISSION_CODE
                        )
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.GALLERY_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.imageSelector(this)
            } else {
                Toast.makeText(
                    this,
                    "Gallery permission denied. This can be changed in settings.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.IMAGE_REQUEST_CODE){
                if(data != null){
                    try{
                        val selectedImageURI = data.data!!

                        img_profile_pic.setImageURI(Uri.parse(selectedImageURI.toString()))
                    }
                    catch (e : IOException){
                        e.printStackTrace()
                        Toast.makeText(this, "Cannot use this image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}