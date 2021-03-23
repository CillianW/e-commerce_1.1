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
import androidx.appcompat.widget.AppCompatEditText

//this class allows us the add the Montserrat-Bold font to the edit text fields in the app
class EditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    init{
        applyFont()
    }

    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}