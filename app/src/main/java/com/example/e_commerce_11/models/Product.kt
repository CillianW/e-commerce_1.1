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
class Products (
    val id: String = "",
    val productName: String = "",
    val productDescription: String = "",
    val imageURL: String = "",
) : Parcelable