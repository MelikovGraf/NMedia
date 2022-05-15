package ru.netology.NMedia.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.NMedia.R
import ru.netology.NMedia.data.Post
import ru.netology.NMedia.databinding.ItemBinding

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener,
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemBinding,
        listener: PostInteractionListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

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
            binding.likeIcon.setOnClickListener { listener.onLikeClicked(post) }
            binding.menu.setOnClickListener { popupMenu.show() }
            binding.repostIcon.setOnClickListener { listener.onShareClicked(post) }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                authorDate.text = post.date
                authorText.text = post.content
                likeIcon.text = if (post.likedByMe) {
                    getCount(post.likes + 1)
                } else {
                    getCount(post.likes)
                }
                likeIcon.isChecked = post.likedByMe
                repostIcon.text = getCount(post.repost + 1)
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