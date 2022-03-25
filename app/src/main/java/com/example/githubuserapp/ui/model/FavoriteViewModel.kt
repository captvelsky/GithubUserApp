package com.example.githubuserapp.ui.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.UserDao
import com.example.githubuserapp.data.local.UserDatabase
import com.example.githubuserapp.data.local.UserEntity

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: UserDao? = userDatabase?.userDao()

    fun getFavorite(): LiveData<List<UserEntity>>? {
        return userDao?.getFavoriteUser()
    }
}