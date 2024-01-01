package com.example.borneoapps

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Trip(
    val name: String,
    val description: String,
    val photo: Int,
    val location: String
) : Parcelable
