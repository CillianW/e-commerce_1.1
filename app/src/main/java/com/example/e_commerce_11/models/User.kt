package com.example.e_commerce_11.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

//@Parcelize is used to allow us to pass a User object between activities
@Parcelize
class User (
    val id: String = "",
    val firstName: String = "",
    val surname: String = "",
    val email: String = "",
    val gender: String = "",
    val imageURL: String = "",
    val phoneNumber: Long = 0,
    val profileComplete: Int = 0,
    var admin : Int = 0,
    val address1: String? = null,
    val address2: String? = null,
    val address3: String? = null,
    val address4: String? = null,
) : Parcelable