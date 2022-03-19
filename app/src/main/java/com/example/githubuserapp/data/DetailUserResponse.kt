package com.example.githubuserapp.data

data class DetailUserResponse(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val name: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
)
