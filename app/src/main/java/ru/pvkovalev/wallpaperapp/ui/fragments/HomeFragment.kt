package ru.pvkovalev.wallpaperapp.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.R.layout
import ru.pvkovalev.wallpaperapp.databinding.FragmentHomeBinding
import ru.pvkovalev.wallpaperapp.paging.loadingstate.LoaderStateAdapter
import ru.pvkovalev.wallpaperapp.recyclerview.RecyclerViewAdapter
import ru.pvkovalev.wallpaperapp.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()
    private val recyclerViewAdapter by lazy { RecyclerViewAdapter() }
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var isPostNotificationGranted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewModel.homePage.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter { (recyclerViewAdapter.retry()) },
            footer = LoaderStateAdapter { (recyclerViewAdapter.retry()) },
        )
        recyclerViewAdapter.addLoadStateListener { loadState ->
            binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            handleErrorState(loadState)
        }
        recyclerViewAdapter.onItemClick = {
            val direction = MainFragmentDirections.actionMainFragmentToDownloadFragment(it, null)
            view?.findNavController()?.navigate(direction)
        }
        binding.buttonRetry.setOnClickListener {
            recyclerViewAdapter.retry()
        }
    }

    private fun handleErrorState(loadStates: CombinedLoadStates) {
        val errorState = loadStates.source.append as? LoadState.Error
            ?: loadStates.source.prepend as? LoadState.Error
        errorState?.let {
            Toast.makeText(context, getString(R.string.retry), Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) isPostNotificationGranted = true
            }
        isPostNotificationGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}