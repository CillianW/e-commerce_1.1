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
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
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
import java.io.IOException


private lateinit var userDetails : User
private var profilePictureURI : Uri? = null

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        setupActionBar()

        //create a new User object and assign any parcelized user objects from the intent to it
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!


            //disable editing and assign the appropriate user details to the relevant fields
            //et_change_first_name.isEnabled = false
            et_change_first_name.setText(userDetails.firstName)

            //et_change_surname.isEnabled = false
            et_change_surname.setText(userDetails.surname)

            et_change_emailID.isEnabled = false
            et_change_emailID.setText(userDetails.email)
            et_change_emailID.setTextColor(resources.getColor(R.color.grey))

            if(userDetails.profileComplete != 0){
                Glide.with(this).load("${userDetails.imageURL}")
                    .placeholder(R.drawable.empty_profile_pic).into(img_profile_pic)

                et_change_mobile.setText(userDetails.phoneNumber.toString())
            }
        }


        //setOnClickListeners for the relevant buttons / items
        img_profile_pic.setOnClickListener(this)
        btn_save_details.setOnClickListener(this)
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

                R.id.btn_save_details -> {
                    if(verifyUserProfileIsComplete()){

                        //hashMap used for storing key:value pairs
                        val userHashMap = HashMap<String, Any>()

                        val mobileNumber = et_change_mobile.text.toString().trim{ it <= ' '}

                        val gender = if(rb_male.isChecked){
                            Constants.MALE
                            }
                            else{
                                Constants.FEMALE
                            }

                        //pass the mobile number and gender in the appropriate format to the hashMap
                        userHashMap[Constants.PHONE_NUMBER] = mobileNumber.toLong()
                        userHashMap[Constants.GENDER] = gender

                        //if a profile picture has been selected, upload it to the fireStore database
                        if(profilePictureURI != null){
                            val fileType  = MimeTypeMap.getSingleton()
                                .getExtensionFromMimeType(contentResolver.getType(profilePictureURI!!))

                            val fireStoreReference : StorageReference = FirebaseStorage.getInstance()
                                .reference.child(userDetails.id + "profilePic"
                                        + System.currentTimeMillis() + "." + fileType)

                            fireStoreReference.putFile(profilePictureURI!!)
                                .addOnSuccessListener { taskSnapshot ->
                                    taskSnapshot.metadata!!.reference!!.downloadUrl
                                        .addOnSuccessListener { url ->
                                            createProfilePicKeyValuePair(url.toString())
                                        }
                                }
                                .addOnFailureListener{ e ->
                                    Log.e(
                                        javaClass.simpleName,
                                        "Error uploading photo",
                                        e
                                    )
                                }
                        }

                        displayProgressDialogue(resources.getString(R.string.please_wait))

                        //update the profile
                        FireStoreClass().updateUserProfile(this, userHashMap)
                    }
                }

            }
        }
    }

    //process the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //if the returned request code matches our own request code and the user has granted the permission,
        //run the phone's image selector function
        //else, display an error message
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

    //checks that the selected image from the Constants.imageSelector function has been selected successfully,
    //if so, assign it to img_profile_pic
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.IMAGE_REQUEST_CODE){
                if(data != null){
                    try{
                        profilePictureURI = data.data!!

                        //load the image into the img_profile_pic using the URI
                        //img_profile_pic.setImageURI(Uri.parse(profilePictureURI.toString()))

                        //alternatively, we can use the Glide class to perform this action
                        //The Glide class is useful because it accepts various file types as an argument
                        Glide.with(this).load(profilePictureURI)
                            .placeholder(R.drawable.empty_profile_pic).into(img_profile_pic)
                    }
                    catch (e : IOException){
                        e.printStackTrace()
                        Toast.makeText(this, "Cannot use this image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    //ensure the user has filled out their profile
    // we don't need to check first name, surname or email as we have already set the text in the onCreate method
    private fun verifyUserProfileIsComplete() : Boolean{
        return when{
            TextUtils.isEmpty(et_change_mobile.text.toString().trim{it <= ' '}) -> {
                displaySnackBar(resources.getString(R.string.enter_mobile), true)
                false
            }
            else -> {
                true
            }
        }
    }

    //displays a success message to the user upon successful update of user details
    //also launches the main activity and finishes the UserProfileActivity
    fun userInfoUpdatedSuccessfully(){
        dismissProgressDialogue()
        Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT)

        //run next activity after a 2.5 second delay
        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            },
            500)
    }

    private fun createProfilePicKeyValuePair(url : String){
        var userHashMap = HashMap<String, Any>()

        userHashMap[Constants.IMAGE_URL] = url
        userHashMap[Constants.PROFILE_COMPLETE] = 1

        FireStoreClass().updateUserProfilePicture(this, userHashMap)
    }

    //this function sets up the back button at the of the screen
    private fun setupActionBar(){
        setSupportActionBar(toolbar_user_profile_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        toolbar_user_profile_activity.setNavigationOnClickListener{
            onBackPressed()
            finish()}
    }

}