package com.example.e_commerce_11.activities
/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_settings.*


//register activity allows new members to create an account
class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupActionBar()

        login_already_registered.setOnClickListener{
            onBackPressed()
        }

        btn_register.setOnClickListener{
            registerUser()
        }
    }


    //this function sets the error message to be displayed if a user does not enter details in a field
    private fun verifyUserDetails() : Boolean{
        return when{
            TextUtils.isEmpty(edit_first_name.text.toString().trim{it <= ' '}) -> {
                displaySnackBar(resources.getString(R.string.error_first_name), true)
                false
            }
            TextUtils.isEmpty(edit_surname.text.toString().trim{it <= ' '}) -> {
                displaySnackBar(resources.getString(R.string.error_surname), true)
                false
            }
            TextUtils.isEmpty(edit_email.text.toString().trim{it <= ' '}) -> {
                displaySnackBar(resources.getString(R.string.error_email_address), true)
                false
            }
            TextUtils.isEmpty(edit_password.text.toString().trim{it <= ' '}) -> {
                displaySnackBar(resources.getString(R.string.error_password), true)
                false
            }
            TextUtils.isEmpty(edit_confirm_password.text.toString().trim{it <= ' '}) -> {
                displaySnackBar(resources.getString(R.string.error_confirm_password), true)
                false
            }
            edit_password.text.toString().trim{it <= ' '} != edit_confirm_password.text.toString().trim{it <= ' '} -> {
                displaySnackBar(resources.getString(R.string.error_passwords_don_t_match), true)
                false
            }
            !checkbox_t_and_c.isChecked -> {
                displaySnackBar(resources.getString(R.string.error_t_and_c_not_checked), true)
                false
            }
            else -> {
                true
            }
        }
    }

    //registers a new user on Firebase
    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (verifyUserDetails()) {

            displayProgressDialogue(resources.getString(R.string.please_wait))

            val email: String = edit_email.text.toString().trim { it <= ' ' }
            val password: String = edit_password.text.toString().trim { it <= ' ' }

            // Create an instance and register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            //create a new user object
                            val user = User(
                                firebaseUser.uid,
                                edit_first_name.text.toString().trim{ it <= ' '},
                                edit_surname.text.toString().trim{ it <= ' '},
                                edit_email.text.toString().trim{ it <= ' '}
                            )

                            //pass the new user object to the FireStore
                            FireStoreClass().registerUser(this@RegisterActivity, user)

                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    finish()
                                },
                                1000)
                        } else {
                            dismissProgressDialogue()

                            // If the registering is not successful then show error message.
                            displaySnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    //displays a success message to the user upon successful registration
    fun userRegisteredSuccessfully(){

        dismissProgressDialogue()

        Toast.makeText(this@RegisterActivity,
            resources.getString(R.string.user_registered_successfully),
            Toast.LENGTH_SHORT).show()
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

}