package ru.pvkovalev.wallpaperapp.presentation.fragments.specificcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.pvkovalev.wallpaperapp.databinding.FragmentSpecificCategoryBinding
import ru.pvkovalev.wallpaperapp.presentation.adapters.RecyclerViewAdapter
import ru.pvkovalev.wallpaperapp.presentation.fragments.categories.CategoryViewModel

@AndroidEntryPoint
class SpecificCategoryFragment : Fragment() {

    private var _binding: FragmentSpecificCategoryBinding? = null
    private val binding get() = _binding!!
    private val recyclerViewAdapter by lazy {
        RecyclerViewAdapter()
    }
    private val viewModel: CategoryViewModel by viewModels()
    private val args: SpecificCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpecificCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.categoryName.text = args.categoryName
        lifecycleScope.launch {
            viewModel.getCategoryWallpapers(args.categoryName).collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
        addCallback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addCallback() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        binding.wallCategoriesRecyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.onItemClick = {
            val direction =
                SpecificCategoryFragmentDirections.actionSpecificCategoryFragmentToDownloadFragment(
                    it,
                    null
                )
            view?.findNavController()?.navigate(direction)
        }
//            recyclerViewAdapter.withLoadStateHeaderAndFooter(
//                header = LoaderStateAdapter { recyclerViewAdapter.retry() },
//                footer = LoaderStateAdapter { recyclerViewAdapter.retry() }
//            )
//        recyclerViewAdapter.addLoadStateListener { loadState ->
//            binding.wallCategoriesRecyclerView.isVisible =
//                loadState.source.refresh is LoadState.NotLoading
//            binding.categoryProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
//            binding.categoryButtonRetry.isVisible = loadState.source.refresh is LoadState.Error
//            handleErrorState(loadState)
//        }
//        binding.categoryButtonRetry.setOnClickListener {
//            recyclerViewAdapter.retry()
//        }
    }

//    private fun handleErrorState(loadStates: CombinedLoadStates) {
//        val errorState = loadStates.source.append as? LoadState.Error
//            ?: loadStates.source.prepend as? LoadState.Error
//        errorState?.let {
//            Toast.makeText(context, getString(R.string.retry), Toast.LENGTH_LONG).show()
//        }
//    }

}