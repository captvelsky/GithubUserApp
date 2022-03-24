package com.example.githubuserapp.retrofit

import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.data.DetailUserResponse
import com.example.githubuserapp.data.User
import com.example.githubuserapp.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.apiKey}")
    fun getUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.apiKey}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.apiKey}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.apiKey}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}
