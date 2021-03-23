package com.example.e_commerce_11.activities
/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_11.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


//register activity allows new members to create an account
class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupActionBar()

        login_already_registered.setOnClickListener{
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener{
            verifyUserDetails()
        }

    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_register_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }

        toolbar_register_activity.setNavigationOnClickListener{
            onBackPressed()
            finish()}
    }

    //this function sets the error message to be displayed if a user does not enter details in a field
    fun verifyUserDetails() : Boolean{
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
                displaySnackBar(resources.getString(R.string.success), false)
                true
            }
        }
    }
}