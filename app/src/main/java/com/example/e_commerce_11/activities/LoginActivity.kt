package com.example.e_commerce_11.activities
/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


//login activity allows an existing member to sign in to the app and
//allows new members to access the register activity
class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        tv_register.setOnClickListener(this)
        tv_forgot_password.setOnClickListener(this)
        btn_login.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        if(view != null){

            when(view.id){

                R.id.tv_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.tv_register -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {
                    loginUser()
                }
            }
        }
    }

    //this function checks that an email address and password have been entered
    //i.e. the fields have not been left blank
    private fun validateLogin() : Boolean{
        return when {
                TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                    displaySnackBar(resources.getString(R.string.error_email_address), true)
                    false
                }
                TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                    displaySnackBar(resources.getString(R.string.error_password), true)
                    false
                }
                else -> {
                    true
                }
            }
    }

    //this function logs the user in using Firebase Authentication
    private fun loginUser(){

        if (validateLogin()){

            displayProgressDialogue(resources.getString(R.string.please_wait))

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }


            //logging in using FireBase authentication
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(){task ->

                    if(task.isSuccessful) {
                        FireStoreClass().getUserDetails(this@LoginActivity)
                    }
                    else{
                        dismissProgressDialogue()
                        displaySnackBar(task.exception!!.message.toString(), true)
                    }

                }

        }
    }

    fun userLoggedInSuccess(user: User){

        dismissProgressDialogue()

        //log the user info
        Log.i("First Name", user.firstName)
        Log.i("Surname", user.surname)
        Log.i("Email", user.email)


        //if the user's profile is not fully completed, send them to the profile activity
        //else, send them to the main activity
        if(user.profileComplete == 0) {
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)

            //we can send parcelized objects to the next activity using the putExtra() function
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        }
        else{
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

        //finish the login activity so the user can't use the back button to return to login
        //once they have successfully logged in
        finish()
    }
}
