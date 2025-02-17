package com.vks.mvvmexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.vks.mvvmexample.R
import com.vks.mvvmexample.databinding.LayoutItemPostBinding
import com.vks.repository.model.Posts

class PostsAdapter() :
    RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    var postsList: List<Posts> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            LayoutItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(postsList[position])
    }


    inner class PostsViewHolder(private val binding: LayoutItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(posts: Posts) {
            binding.titleText.text = posts.title
            binding.bodyText.text = posts.body
            binding.tvLikes.text = (posts.reactions?.likes ?: 0).toString()
            binding.tvDisLikes.text = (posts.reactions?.dislikes ?: 0).toString()
            binding.tvViewCount.text = posts.views.toString()

            if (posts.tags?.isNotEmpty() == true)
                for (i in 0..<(posts.tags?.size ?: 0)) {
                    val layoutInflater = LayoutInflater.from(binding.root.context)
                    val view = layoutInflater!!.inflate(
                        R.layout.item_tags,
                        binding.chipGroup,
                        false
                    ) as Chip

                    val chipText = view.findViewById<Chip>(R.id.chip)
                    posts.tags?.get(i).let {
                        chipText.text = it
                    }
                    binding.chipGroup.addView(view)
                }
        }
    }
}