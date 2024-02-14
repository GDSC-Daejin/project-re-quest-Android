package com.example.a2024solutionchallenge

import com.example.a2024solutionchallenge.data.LoginGoogleResponse
import com.example.a2024solutionchallenge.data.LoginResponsesData
import com.example.a2024solutionchallenge.data.TokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import javax.security.auth.callback.Callback

interface ApiService {

    @POST("")
    fun addWrite(@Body data : LoginGoogleResponse) : Call<LoginResponsesData>
    /*@POST("/auth/google")
    fun addUserInfoData(@Body token: TokenRequest) : retrofit2.Call<LoginResponsesData>
    */
}