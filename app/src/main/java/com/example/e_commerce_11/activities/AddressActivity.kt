package com.example.e_commerce_11.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import kotlinx.android.synthetic.main.activity_addres.*
import kotlinx.android.synthetic.main.activity_settings.*

private lateinit var userDetails : User

class AddressActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addres)

        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!

            et_change_address_line_1.setText(userDetails.address1)
            et_change_address_line_2.setText(userDetails.address2)
            et_change_address_line_3.setText(userDetails.address3)
            et_change_address_line_4.setText(userDetails.address4)
        }



        btn_save_address.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v != null){

            when(v.id){

                R.id.btn_save_address -> {

                    val userHashMap = HashMap<String, Any>()

                    userHashMap[Constants.ADDRESS_1] = et_change_address_line_1.text.toString()
                    userHashMap[Constants.ADDRESS_2] = et_change_address_line_2.text.toString()
                    userHashMap[Constants.ADDRESS_3] = et_change_address_line_3.text.toString()
                    userHashMap[Constants.ADDRESS_4] = et_change_address_line_4.text.toString()

                    FireStoreClass().updateUserProfile(this, userHashMap)

                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            startActivity(Intent(this, SettingsActivity::class.java))
                            finish()
                        },
                        500)
                }
            }
        }
    }

    //this function sets up the back button at the of the screen
    private fun setupActionBar(){
        setSupportActionBar(toolbar_address_activity)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white)
        }

        toolbar_address_activity.setNavigationOnClickListener{
            onBackPressed()
            finish()}
    }
}