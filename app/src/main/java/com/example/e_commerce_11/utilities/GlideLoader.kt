package com.example.e_commerce_11.utilities

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.e_commerce_11.R
import java.io.IOException

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */
class GlideLoader(val context: Context) {

    fun loadUserProfile(image: String, imageView: ImageView){
        try{
            Glide.with(context).load(image).centerCrop()
                .placeholder(R.drawable.empty_profile_pic)
                .into(imageView)
        }
        catch(e: IOException){
            e.printStackTrace()
        }
    }

    fun loadItem(image: String, imageView: ImageView){
        try{
            Glide.with(context).load(image).centerCrop()
                .placeholder(R.drawable.empty_product)
                .into(imageView)
        }
        catch(e: IOException){
            e.printStackTrace()
        }
    }

}