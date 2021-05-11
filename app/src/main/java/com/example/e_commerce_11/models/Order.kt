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
data class Order (
    var orderID: String = "",
    var orderItemID: String = "",
    var userID: String = "",
    var orderItemName: String = "",
    var orderItemPrice: String = "",
    var orderItemQuantity: String = "0"
) : Parcelable