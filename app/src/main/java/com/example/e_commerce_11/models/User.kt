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
    var firstName: String = "",
    var surname: String = "",
    var email: String = "",
    var gender: String = "",
    var imageURL: String = "",
    var phoneNumber: Long = 0,
    var profileComplete: Int = 0,
    var admin : Int = 0,
    var address1: String? = null,
    var address2: String? = null,
    var address3: String? = null,
    var address4: String? = null,
) : Parcelable