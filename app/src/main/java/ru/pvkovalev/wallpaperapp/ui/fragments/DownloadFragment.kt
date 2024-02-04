package ru.pvkovalev.wallpaperapp.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.FragmentDownloadBinding
import ru.pvkovalev.wallpaperapp.db.WallpaperEntity
import ru.pvkovalev.wallpaperapp.db.WallpaperRepository
import ru.pvkovalev.wallpaperapp.viewmodels.DownloadFragmentViewModel
import ru.pvkovalev.wallpaperapp.viewmodels.DownloadFragmentViewModelFactory

class DownloadFragment : Fragment() {

    private val binding: FragmentDownloadBinding by viewBinding()
    private val args: DownloadFragmentArgs by navArgs()
    private val viewModel: DownloadFragmentViewModel by viewModels {
        DownloadFragmentViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage()
        addCallback()
        bottomSheet()
        binding.favoritesButton.setOnClickListener {
            insertWallpaper()
        }
    }

    private fun loadImage() {
        val image = if (args.args != null) args.args?.urls?.medium else args.args2?.wallpaperUrl
        Glide.with(this)
            .load(image)
            .centerCrop()
            .into(binding.downloadImageView)

        Glide.with(this)
            .load(image)
            .centerCrop()
            .override(3, 3)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.constraintBackground.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun addCallback() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bottomSheet() {
        val image = args.args?.urls?.large
        val bottomSheet = BottomSheetFragment.newInstance(image)
        binding.downloadButton.setOnClickListener {
            bottomSheet.show(requireActivity().supportFragmentManager, "BottomSheetFragment")
        }
    }

    private fun insertWallpaper() {
        val wallpaperUrl = if (args.args != null) args.args?.urls?.medium else args.args2?.wallpaperUrl
        val wallpaperItem = WallpaperEntity(0, wallpaperUrl)
        viewModel.addWallpaper(wallpaperItem)
        Toast.makeText(context, getString(R.string.Save_to_favorites), Toast.LENGTH_SHORT).show()
    }
}