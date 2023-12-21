package ru.pvkovalev.wallpaperapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.pvkovalev.wallpaperapp.data.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.databinding.ItemUnsplashPhotoBinding
import javax.inject.Inject

class UnsplashAdapter @Inject constructor() :
    PagingDataAdapter<ResponseUnsplashPhoto, UnsplashAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    inner class PhotoViewHolder(
       private val binding: ItemUnsplashPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: ResponseUnsplashPhoto?) {
            binding.apply {
                if (photo != null) {
                    imageView.load(photo.urls.small)
                } else imageView.setImageDrawable(null)
                root.setOnClickListener {
                    onItemClickListener?.let {
                        photo?.let { pic -> it(pic) }
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseUnsplashPhoto) -> Unit)? = null
    fun setOnItemClickListener(listener: (ResponseUnsplashPhoto) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<ResponseUnsplashPhoto>() {
            override fun areItemsTheSame(
                oldItem: ResponseUnsplashPhoto,
                newItem: ResponseUnsplashPhoto
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ResponseUnsplashPhoto,
                newItem: ResponseUnsplashPhoto
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoViewHolder(
            ItemUnsplashPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
    }

    override fun getItemViewType(position: Int) = position
}