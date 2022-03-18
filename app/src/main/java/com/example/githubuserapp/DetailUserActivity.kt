package com.example.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profile"

        val user = intent.getParcelableExtra<User>(EXTRA_USERNAME) as User
        Glide.with(baseContext)
            .load(user.avatar)
            .circleCrop()
            .into(binding.imgUserAvatar)
        binding.tvUserName.text = user.name
        binding.tvUserUsername.text = user.username
        binding.tvUserLocation.text = user.location
        binding.tvUserCompany.text = user.company
        binding.tvUserFollowers.text = user.followers.plus(" Followers")
        binding.tvUserFollowing.text = user.following.plus(" Following")
        binding.tvUserRepository.text = user.repository.plus(" Repositories")
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}