package com.example.e_commerce_11.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */
@Parcelize
data class CartItem (
    var cartItemID: String = "",
    var userID: String = "",
    var cartItemName: String = "",
    var cartItemDescription: String = "",
    var cartItemImgURI: String = "",
    var cartItemPrice: String = "",
    var cartItemQuantity: String = "0"
) : Parcelable