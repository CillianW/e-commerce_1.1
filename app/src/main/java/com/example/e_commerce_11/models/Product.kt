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
class Product (
    var productID: String = "",
    var productName: String = "",
    var productDescription: String = "",
    var productImgURI: String = "",
    var price: String = "",
    var quantity: String = ""
) : Parcelable