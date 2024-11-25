package ru.pvkovalev.wallpaperapp.data.api.paging.loadingstate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.ItemLoaderBinding

class LoadStateViewHolder(
    private val binding: ItemLoaderBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButtonItem.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text =
                binding.root.context.resources.getString(R.string.no_internet_connection)
        }

        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButtonItem.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading

    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loader, parent, false)
            val binding = ItemLoaderBinding.bind(view)
            return LoadStateViewHolder(binding, retry)
        }
    }

}