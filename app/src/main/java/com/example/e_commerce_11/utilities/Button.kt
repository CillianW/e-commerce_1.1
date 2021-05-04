package com.example.e_commerce_11.utilities
/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.example.e_commerce_11.R

//this class allows us the add the Montserrat-Bold font to the buttons in the app
class Button(context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {

    init {
        applyFont()
    }

    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}