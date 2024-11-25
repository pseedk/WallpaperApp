package ru.pvkovalev.wallpaperapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.pvkovalev.wallpaperapp.databinding.ItemRecyclerViewBinding
import ru.pvkovalev.wallpaperapp.domain.model.WallpaperItem

class FavoritesAdapter :
    ListAdapter<WallpaperItem, FavoritesAdapter.MyViewHolder>(DiffUtilCallback) {

    var onItemClick: ((WallpaperItem) -> Unit)? = null

    inner class MyViewHolder(
        private val binding: ItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wallpaperEntity: WallpaperItem) {
            Glide.with(itemView.context)
                .load(wallpaperEntity.wallpaperUrl)
                .centerCrop()
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder =
        MyViewHolder(
            ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem!!)
        }
    }


    object DiffUtilCallback : DiffUtil.ItemCallback<WallpaperItem>() {
        override fun areItemsTheSame(
            oldItem: WallpaperItem,
            newItem: WallpaperItem
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: WallpaperItem,
            newItem: WallpaperItem
        ) = oldItem == newItem
    }
}




