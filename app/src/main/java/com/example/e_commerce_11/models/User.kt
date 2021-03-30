package com.example.e_commerce_11.models

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

class User (
    val id: String = "",
    val firstName: String = "",
    val surname: String = "",
    val email: String = "",
    val gender: String = "",
    val imageURL: String = "",
    val phoneNumber: Long = 0,
    val profileComplete: Int = 0
)