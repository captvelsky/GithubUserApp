package com.example.githubuserapp.ui.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.DetailUserResponse
import com.example.githubuserapp.data.local.UserDao
import com.example.githubuserapp.data.local.UserDatabase
import com.example.githubuserapp.data.local.UserEntity
import com.example.githubuserapp.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: UserDao? = userDatabase?.userDao()

    fun setUserDetail(username: String) {
        ApiConfig.getApiService().getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addFavorite(id: Int, username: String, avatarUrl: String) {
        viewModelScope.launch {
            userDao?.addFavoriteUser(UserEntity(id, username, avatarUrl))
        }
    }

    fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            userDao?.deleteFavoriteUser(id)
        }
    }

    suspend fun isFavorite(id: Int) = userDao?.isFavoriteUser(id)

    companion object {
        private const val TAG = "MainActivity"
    }
}