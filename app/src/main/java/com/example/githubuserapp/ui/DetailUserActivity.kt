package com.example.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapter.SectionsPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailUserBinding
import com.example.githubuserapp.ui.model.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profile"

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        var isChecked = false
        val bundle = Bundle()

        bundle.putString(EXTRA_USERNAME, username)
        showLoading(true)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .circleCrop().into(imgUserAvatar)
                    tvUserName.text = it.name
                    tvUserUsername.text = it.login
                    tvUserLocation.text = it.location
                    tvUserFollowers.text = "${it.followers} Followers"
                    tvUserFollowing.text = "${it.following} Following"
                    tvUserRepository.text = "${it.public_repos} Repository"
                }
                showLoading(false)
            }
        }

        viewModel.viewModelScope.launch {
            val count = viewModel.checkUser(id)
            if (count != null) {
                if (count > 0) {
                    binding.favoriteButton.isChecked = true
                    isChecked = true
                } else {
                    binding.favoriteButton.isChecked = false
                    isChecked = false
                }
            }
        }

        binding.apply {
            favoriteButton.setOnClickListener {
                if (isChecked) {
                    viewModel.deleteFavorite(id)
                    isChecked = !isChecked
                } else {
                    viewModel.addFavorite(id, username.toString(), avatarUrl.toString())
                    isChecked = !isChecked
                }
                favoriteButton.isChecked = isChecked
            }
        }

        val tabs: TabLayout = binding.tabLayout
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favoriteMenu -> {
                Intent(this@DetailUserActivity, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.settingMenu -> {
                Intent(this@DetailUserActivity, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text1, R.string.tab_text2)
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "avatar_url"
    }
}