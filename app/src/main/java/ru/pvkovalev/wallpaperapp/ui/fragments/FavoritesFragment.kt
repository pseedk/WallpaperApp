package ru.pvkovalev.wallpaperapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.FragmentFavoritesBinding
import ru.pvkovalev.wallpaperapp.model.ResponseUnsplashPhoto
import ru.pvkovalev.wallpaperapp.recyclerview.FavoritesAdapter
import ru.pvkovalev.wallpaperapp.viewmodels.FavoritesFragmentViewModel
import ru.pvkovalev.wallpaperapp.viewmodels.FavoritesFragmentViewModelFactory

class FavoritesFragment : Fragment() {

    private val binding: FragmentFavoritesBinding by viewBinding()
    private val recyclerViewAdapter by lazy { FavoritesAdapter() }
    private val viewModel: FavoritesFragmentViewModel by viewModels {
        FavoritesFragmentViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = recyclerViewAdapter
        viewModel.getAllNotes().observe(viewLifecycleOwner) {
            recyclerViewAdapter.submitList(it)
        }
        recyclerViewAdapter.onItemClick = {
            val direction = MainFragmentDirections.actionMainFragmentToDownloadFragment(null, it)
            view?.findNavController()?.navigate(direction)
        }
//        viewModel.getAllWallpapers.observe(viewLifecycleOwner) {
//            recyclerViewAdapter.submitList(it)
//        }
    }
}