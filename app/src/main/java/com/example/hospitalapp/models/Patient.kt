package com.example.hospitalapp.models

data class Patient(
    var id: String? = null,
    val name:String? = null,
    val gender: String? = null,
    val nationality: String? = null,
    val phonenumber: String? = null,
    val age: String? = null,
    val diagnosis: String? = null,
    val nextofkin: String? = null,
    val imageUrl: String? = null,
)
