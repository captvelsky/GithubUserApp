package com.example.githubuserapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserapp.data.User

class AdapterDiffUtils(
    private val oldList: ArrayList<User>,
    private val newList: ArrayList<User>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].login != newList[newItemPosition].login -> {
                false
            }
            oldList[oldItemPosition].avatar_url != newList[newItemPosition].avatar_url -> {
                false
            }
            else -> true
        }
    }
}