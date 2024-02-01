package com.example.a2024solutionchallenge.data

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("token")
    val token : String
)