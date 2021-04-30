package com.example.e_commerce_11.activities
/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_11.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splash_page.*

//this is the first page displayed when the app is started
class SplashPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_page)

        //run next activity after a 2.5 second delay
        Handler(Looper.getMainLooper()).postDelayed(
                {
                    //check for current user
                    val user  = Firebase.auth.currentUser

                    //if the current user is null, they are not logged in so we launch the login activity
                    //if the are logged in, we can go straight to the dashboard activity
                    if(user != null){
                        startActivity(Intent(this@SplashPageActivity, DashboardActivity::class.java))
                        finish()
                    }
                    else{
                        startActivity(Intent(this@SplashPageActivity, LoginActivity::class.java))
                        finish()
                    }
                },
                1000)
    }
}