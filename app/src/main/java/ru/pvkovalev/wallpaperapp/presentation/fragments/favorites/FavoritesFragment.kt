package ru.pvkovalev.wallpaperapp.presentation.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.FragmentFavoritesBinding
import ru.pvkovalev.wallpaperapp.presentation.adapters.FavoritesAdapter
import ru.pvkovalev.wallpaperapp.presentation.fragments.main.MainFragmentDirections

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val binding: FragmentFavoritesBinding by viewBinding()
    private val recyclerViewAdapter by lazy { FavoritesAdapter() }
    private val viewModel: FavoritesFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.wallpapers.collect {
                recyclerViewAdapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.onItemClick = {
            val direction = MainFragmentDirections.actionMainFragmentToDownloadFragment(null, it)
            view?.findNavController()?.navigate(direction)
        }
    }
}