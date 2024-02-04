package ru.pvkovalev.wallpaperapp.ui.fragments

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.FragmentSearchBinding
import ru.pvkovalev.wallpaperapp.recyclerview.RecyclerViewAdapter
import ru.pvkovalev.wallpaperapp.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel by viewModels<SearchViewModel>()
    private val recyclerViewAdapter by lazy { RecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.searchRecyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.onItemClick = {
            val direction = MainFragmentDirections.actionMainFragmentToDownloadFragment(it, null)
            view?.findNavController()?.navigate(direction)
        }
    }

    private fun setupSearchView() {
        binding.svSearch.apply {

            onActionViewExpanded()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchItems(query.orEmpty())
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.searchResults.collectLatest { pagingData ->
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