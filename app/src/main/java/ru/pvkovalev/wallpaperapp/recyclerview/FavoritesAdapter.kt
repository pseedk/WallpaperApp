package ru.pvkovalev.wallpaperapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.pvkovalev.wallpaperapp.databinding.ItemRecyclerViewBinding
import ru.pvkovalev.wallpaperapp.db.WallpaperEntity
import ru.pvkovalev.wallpaperapp.model.ResponseUnsplashPhoto

class FavoritesAdapter :
    ListAdapter<WallpaperEntity, FavoritesAdapter.MyViewHolder>(DiffUtilCallback) {

    var onItemClick: ((WallpaperEntity) -> Unit)? = null

    inner class MyViewHolder(
        private val binding: ItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wallpaperEntity: WallpaperEntity) {
            Glide.with(itemView.context)
                .load(wallpaperEntity.wallpaperUrl)
                .centerCrop()
                .into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapter.MyViewHolder =
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


    object DiffUtilCallback : DiffUtil.ItemCallback<WallpaperEntity>() {
        override fun areItemsTheSame(
            oldItem: WallpaperEntity,
            newItem: WallpaperEntity
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: WallpaperEntity,
            newItem: WallpaperEntity
        ) = oldItem == newItem
    }
}




