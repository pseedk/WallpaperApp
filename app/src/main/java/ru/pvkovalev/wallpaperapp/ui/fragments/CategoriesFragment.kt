package ru.pvkovalev.wallpaperapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.FragmentCategoriesBinding
import ru.pvkovalev.wallpaperapp.recyclerview.CategoryAdapter
import ru.pvkovalev.wallpaperapp.utils.ApiListCategory

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViewAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = CategoryAdapter(ApiListCategory.list)
        binding.categoryRecyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.onItemClick = {
            val action = MainFragmentDirections.actionMainFragmentToSpecificCategoryFragment(it.categoryName)
            findNavController().navigate(action)
        }
    }
}