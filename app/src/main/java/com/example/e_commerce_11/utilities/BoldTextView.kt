package com.example.e_commerce_11.utilities


//this class allows us to add the font Monterrat-Bold to any TextView in an xml file

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class BoldTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    //initialise the class
    init{
        applyFont()
    }

//    set the Typeface to the Montserrat-Bold font which is stored in the assets folder
    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}