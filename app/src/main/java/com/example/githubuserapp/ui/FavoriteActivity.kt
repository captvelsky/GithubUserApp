package com.example.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.UserAdapter
import com.example.githubuserapp.data.User
import com.example.githubuserapp.data.local.UserEntity
import com.example.githubuserapp.databinding.ActivityFavoriteBinding
import com.example.githubuserapp.ui.model.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.getFavorite()?.observe(this) {
            if (it != null) {
                adapter.setUserList(mapUsers(it))
            }
        }
    }

    private fun mapUsers(users: List<UserEntity>): ArrayList<User> {
        val res = ArrayList<User>()
        for (user in users) {
            val mappedUsers = User(
                user.login,
                user.id,
                user.avatar_url
            )
            res.add(mappedUsers)
        }
        return res
    }
}