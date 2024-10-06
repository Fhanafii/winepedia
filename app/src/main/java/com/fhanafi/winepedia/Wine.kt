package com.fhanafi.winepedia

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wine(
    val name: String,
    val description: String,
    val photo: Int,
    val score: String,
    val price: String,
    val imported: String
): Parcelable


