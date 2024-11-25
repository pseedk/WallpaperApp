package ru.pvkovalev.wallpaperapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.pvkovalev.wallpaperapp.databinding.ItemRecyclerViewBinding
import ru.pvkovalev.wallpaperapp.data.api.model.ResponseUnsplashPhoto

class RecyclerViewAdapter : PagingDataAdapter<ResponseUnsplashPhoto, RecyclerViewAdapter.MyViewHolder>(
    DiffUtilCallback
) {

    var onItemClick : ((ResponseUnsplashPhoto) -> Unit)? = null

    inner class MyViewHolder(
        private val binding: ItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(responseUnsplashPhoto: ResponseUnsplashPhoto?) {
            Glide.with(itemView.context)
                .load(responseUnsplashPhoto?.urls?.small)
                .centerCrop()
                .into(binding.imageView)
        }
    }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val currentItem = getItem(position)
            holder.bind(currentItem)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(currentItem!!)
//                val direction = MainFragmentDirections.actionMainFragmentToDownloadFragment(currentItem!!)
//                it.findNavController().navigate(direction)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyViewHolder(
                ItemRecyclerViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    object DiffUtilCallback : DiffUtil.ItemCallback<ResponseUnsplashPhoto>() {
        override fun areItemsTheSame(
            oldItem: ResponseUnsplashPhoto,
            newItem: ResponseUnsplashPhoto
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ResponseUnsplashPhoto,
            newItem: ResponseUnsplashPhoto
        ) = oldItem == newItem
    }
}


