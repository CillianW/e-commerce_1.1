package com.example.e_commerce_11.firestore

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_11.activities.*
import com.example.e_commerce_11.activities.ui.dashboard.DashboardFragment
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {
    private val myFireStore = FirebaseFirestore.getInstance()


    //register the users details on firestore
    fun registerUser(activity: RegisterActivity, user: User) {

        //create a collection called users if it doesn't already exist
        myFireStore.collection(Constants.USERS)
                //user details will be separated into documents, sorted by user IDs
            .document(user.id)
                //add the user details to the document
            .set(user, SetOptions.merge())
                //if user is registered successfully, call the userRegisteredSuccessfully() method
            .addOnSuccessListener {
                activity.userRegisteredSuccessfully()
            }
                //if an error occurs, log it and display a message
            .addOnFailureListener { e ->
                activity.dismissProgressDialogue()
                Log.e(
                    activity.javaClass.simpleName,
                    "User registration failed",
                    e
                )
            }
    }


    //retrieve current user ID which will be used later
    fun getCurrentUserID(): String {

        //get user details from Firebase
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""

        //store the user ID in currentUser variable
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        //return the user ID
        return currentUserID
    }

    //get the user details using user ID
    fun getUserDetails(activity: Activity) {

        //make a get request to the FireStore users collection
        myFireStore.collection(Constants.USERS)
                //find the document by user ID
            .document(getCurrentUserID())
                //get request used to retrieve info
            .get()
                //if successful, store user details to the device
            .addOnSuccessListener { document ->

                //log thr user details
                Log.i(activity.javaClass.simpleName, document.toString())

                //create a User object using the details we just retrieved
                val user = document.toObject(User::class.java)

                //get the shared preferences from the activity using private mode
                val sharedPreferences = activity.getSharedPreferences(
                    Constants.APP_PREFERENCES,
                    Context.MODE_PRIVATE
                )

                //use a SharedPreferences object to add a key:value pair (logged_in_username:first name & last name)
                val editor : SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user!!.firstName} ${user!!.surname}"
                )
                editor.apply()


                when (activity) {
                    is LoginActivity -> {
                        if (user != null) {
                            activity.userLoggedInSuccess(user)
                        }
                    }
                    is SettingsActivity -> {
                        if (user != null) {
                            activity.userDetailsSuccess(user)
                        }
                    }
                }
            }
                //display a message if an error occurs
            .addOnFailureListener { e ->

                when (activity) {
                    is LoginActivity -> {
                        activity.dismissProgressDialogue()
                    }
                    is SettingsActivity ->{
                        activity.dismissProgressDialogue()
                    }
                }

                Log.e(activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e)

            }

    }

    //update the user details on fireStore
    fun updateUserProfile(activity: Activity, userHashMap: HashMap<String, Any>){

        //send an update request to update user details
        myFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
                //if successful, call the userInfoUpdatedSuccessfully function
            .addOnSuccessListener {
                when(activity){
                    is UserProfileActivity -> {
                        activity.userInfoUpdatedSuccessfully()
                    }
                }
            }
                //dismiss the progress dialogue and show the user a message if an error occurs
            .addOnFailureListener{ e ->
                when(activity){
                    is UserProfileActivity -> {
                        activity.dismissProgressDialogue()
                    }
                }
                Log.e(activity.javaClass.simpleName,
                "Error updating the user details ",
                e)
            }
    }

    fun updateUserProfilePicture(activity: Activity, userHashMap: HashMap<String, Any>){

        //send an update request to update user details
        myFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            //dismiss the progress dialogue and show the user a message if an error occurs
            .addOnFailureListener{ e ->
                when(activity){
                    is UserProfileActivity -> {
                        activity.dismissProgressDialogue()
                    }
                }
                Log.e(activity.javaClass.simpleName,
                    "Error updating the user details ",
                    e)
            }
    }
}
