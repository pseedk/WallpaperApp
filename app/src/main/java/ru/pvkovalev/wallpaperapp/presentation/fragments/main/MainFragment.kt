package ru.pvkovalev.wallpaperapp.presentation.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.FragmentMainBinding
import ru.pvkovalev.wallpaperapp.presentation.adapters.ViewPagerAdapter
import ru.pvkovalev.wallpaperapp.presentation.fragments.search.SearchFragment
import ru.pvkovalev.wallpaperapp.presentation.fragments.categories.CategoriesFragment
import ru.pvkovalev.wallpaperapp.presentation.fragments.favorites.FavoritesFragment
import ru.pvkovalev.wallpaperapp.presentation.fragments.home.HomeFragment

class MainFragment : Fragment() {

    private val binding: FragmentMainBinding by viewBinding()

    private val tabTitles = listOf("Random", "Categories", "Search", "Favorites")
    private val fragments =
        listOf(HomeFragment(), CategoriesFragment(), SearchFragment(), FavoritesFragment())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabLayout()
        initToolbar()
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun initViewPager() {
        val pagerAdapter = ViewPagerAdapter(context as FragmentActivity, fragments)
        binding.viewPager.adapter = pagerAdapter
        // binding.viewPager.isUserInputEnabled = false
    }

    private fun initToolbar() {
        binding.toolbar.title = "Wallpapers"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }
}