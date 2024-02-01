package com.example.a2024solutionchallenge.data

data class LoginGoogleResponse(
    //@SerializedName("accessToken")
    var access_token: String = "",
    //@SerializedName("accessTokenExpiresIn")
    var expires_in: Int = 0,
    //@SerializedName("refreshToken")
    var scope: String = "",

    //SerializedName("grantType")
    var token_type: String = "",

    var id_token: String = "",
)
