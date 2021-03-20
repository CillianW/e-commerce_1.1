package com.example.e_commerce_11.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_11.R
import kotlinx.android.synthetic.main.activity_splash_page.*


class SplashPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_page)

        //this removes the status bars at the top of the splash page
        //the if statement allows us to run deprecated code if the user's phone is
        //running an older version of android
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }

        //run MainActivity after a 2.5 second delay
        Handler(Looper.getMainLooper()).postDelayed(
                {
                    startActivity(Intent(this@SplashPageActivity, LoginActivity::class.java))
                    finish()
                },
                2500)
    }
}