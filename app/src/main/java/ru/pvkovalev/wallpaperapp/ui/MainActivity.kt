package ru.pvkovalev.wallpaperapp.ui

import android.app.Dialog
import android.app.WallpaperManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.databinding.ActivityMainBinding
import ru.pvkovalev.wallpaperapp.databinding.DialogLoadingBinding
import ru.pvkovalev.wallpaperapp.utils.SetWallpaperTask
import ru.pvkovalev.wallpaperapp.viewmodel.UnsplashViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: UnsplashViewModel by viewModels()

    @Inject
    lateinit var adapter: UnsplashAdapter

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var wallpaperManager: WallpaperManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.adapter = adapter

            swipeRefreshLayout.setOnRefreshListener {
                adapter.refresh()
            }

            lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest { loadStates ->
                    swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
                }
            }

            lifecycleScope.launch {
                viewModel.photos.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }

            adapter.setOnItemClickListener {
                val loading = Dialog(this@MainActivity)
                val bindingLoading = DialogLoadingBinding.inflate(layoutInflater)
                loading.setContentView(bindingLoading.root)
                loading.setCancelable(false)
                lifecycleScope.launch {
                    loading.show()
                    if (SetWallpaperTask(it.urls.large, wallpaperManager).execute()) {
                        loading.dismiss()
                        Toast.makeText(
                            this@MainActivity,
                            "The wallpaper has been successfully changed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        loading.dismiss()
                        Toast.makeText(
                            this@MainActivity,
                            "Error! Please try again later.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}