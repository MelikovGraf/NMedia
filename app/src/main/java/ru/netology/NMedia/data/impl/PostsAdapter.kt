package ru.netology.NMedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.NMedia.R
import ru.netology.NMedia.data.Post
import ru.netology.NMedia.databinding.ItemBinding

internal class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onRepostClicked: (Post) -> Unit,
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onLikeClicked, onRepostClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemBinding,
        onLikeClicked: (Post) -> Unit,
        onRepostClicked: (Post) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private fun getCount(value: Int): String {
            val thousand = value.toDouble() / 1000
            val thousandCent = ((thousand - thousand.toInt()) * 10)
            val hundredThousand = value.toDouble() / 100
            val million = value.toDouble() / 1_000_000
            val millionCent = ((million - million.toInt()) * 10)
            return when {
                (value < 1000) -> value.toString()
                (value in 1000..99999) -> "${thousand.toInt()}.${thousandCent.toInt()}K"
                (value in 100000..999999) -> "${hundredThousand.toInt()}K"
                else -> "${million.toInt()}.${millionCent.toInt()}M"
            }
        }

        init {
            binding.likeIcon.setOnClickListener { onLikeClicked(post) }
            binding.repostIcon.setOnClickListener { onRepostClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                authorDate.text = post.date
                authorText.text = post.content
                if (post.likedByMe) {likesCount.text = getCount(post.likes + 1)}
                else likesCount.text = getCount(post.likes)
                repostCount.text = getCount(post.repost)
                if (post.likedByMe) {
                    likeIcon.setImageResource(R.drawable.ic_like)
                } else likeIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem

    }
}