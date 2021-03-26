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
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.e_commerce_11.R
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


            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(){task ->

                    dismissProgressDialogue()

                    if(task.isSuccessful) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        displaySnackBar("You have logged in successfully", false)
                    }
                    else{
                        displaySnackBar(task.exception!!.message.toString(), true)
                    }

                }

        }
    }
}
