package com.example.e_commerce_11.activities

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.e_commerce_11.R
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.util.jar.Manifest

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //create a new User object and assign any parcelized user objects from the intent to it
        var userDetails = User()
        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
                userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
            }

        //disable editing and assign the appropriate user details to the relevant fields
        et_change_first_name.isEnabled = false
        et_change_first_name.setText(userDetails.firstName)

        et_change_surname.isEnabled = false
        et_change_surname.setText(userDetails.surname)

        et_change_emailID.isEnabled = false
        et_change_emailID.setText(userDetails.email)

        //on pressing the img_profile_pic button, check if permission to access camera is granted.
        //if not, request permission
        btn_save_details.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Camera permission denied. This can be changed in settings.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object{
        private const val CAMERA_PERMISSION_CODE = 1
    }
}