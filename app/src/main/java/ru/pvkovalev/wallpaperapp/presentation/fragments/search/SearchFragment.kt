package ru.pvkovalev.wallpaperapp.presentation.fragments.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.FragmentSearchBinding
import ru.pvkovalev.wallpaperapp.presentation.adapters.RecyclerViewAdapter
import ru.pvkovalev.wallpaperapp.presentation.fragments.main.MainFragmentDirections

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModels()
    private val recyclerViewAdapter by lazy { RecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
    }

    private fun setupRecyclerView() {
        binding.searchRecyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.onItemClick = {
            val direction = MainFragmentDirections.actionMainFragmentToDownloadFragment(it, null)
            view?.findNavController()?.navigate(direction)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.svSearch.apply {
            isIconified = false
            requestFocus()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.svSearch.hideKeyboard()
    }

    private fun setupSearchView() {
        binding.svSearch.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.searchWallpapers(query.orEmpty()).collectLatest { pagingData ->
                            recyclerViewAdapter.submitData(pagingData)
                        }
                    }
                    hideKeyboard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}