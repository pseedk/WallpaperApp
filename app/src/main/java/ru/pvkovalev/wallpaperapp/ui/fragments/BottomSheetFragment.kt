package ru.pvkovalev.wallpaperapp.ui.fragments

import android.Manifest
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import ru.pvkovalev.wallpaperapp.R
import ru.pvkovalev.wallpaperapp.databinding.BottomSheetBinding
import java.io.File
import java.io.IOException

class BottomSheetFragment() : BottomSheetDialogFragment() {

    private val binding: BottomSheetBinding by viewBinding()
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString(ARG_KEY)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        registerPermission()
        initButtons()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initButtons() {
        binding.downLoadFromNet.setOnClickListener { checkPermission() }
        binding.setAsBackground.setOnClickListener { setAsBackground() }
        binding.setAsLockscreen.setOnClickListener { setAsLockScreen() }
    }

    private fun registerPermission() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) downloadImageFromNet(imageUrl)
            }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                downloadImageFromNet(imageUrl)
            }

            shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES) -> {
                Toast.makeText(context, getString(R.string.grant_permission), Toast.LENGTH_LONG)
                    .show()
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }

            else -> permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
    }

    private fun downloadImageFromNet(url: String?) {
        try {
            val downloadManager =
                requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val imageUrl = Uri.parse(url)
            val request = DownloadManager.Request(imageUrl)
            request.apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                setMimeType("image/*")
                setAllowedOverRoaming(false)
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setTitle("WallpaperApp")
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + "image" + ".jpg"
                )
            }
            downloadManager.enqueue(request)
            Toast.makeText(context, getString(R.string.downloading), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                getString(R.string.download_failed, e.message), Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setAsBackground() {
        try {
            val wallpaperManager = WallpaperManager.getInstance(requireContext())
            val image = activity?.findViewById<ShapeableImageView>(R.id.download_image_view)
            if (image?.drawable == null) {
                Toast.makeText(context, "Wait to download", Toast.LENGTH_LONG).show()
            } else {
                val bitmap = (image.drawable as BitmapDrawable).bitmap
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                Toast.makeText(context, getString(R.string.set_background_done), Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: IOException) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun setAsLockScreen() {
        try {
            val wallpaperManager = WallpaperManager.getInstance(requireContext())
            val image = activity?.findViewById<ShapeableImageView>(R.id.download_image_view)
            if (image?.drawable == null) {
                Toast.makeText(context, "Wait to download", Toast.LENGTH_LONG).show()
            } else {
                val bitmap = (image.drawable as BitmapDrawable).bitmap
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                Toast.makeText(context, getString(R.string.set_lock_screen_done), Toast.LENGTH_LONG)
                    .show()
            }
        } catch (e: IOException) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {

        private const val ARG_KEY = "arg_key"

        @JvmStatic
        fun newInstance(imageUrl: String?) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_KEY, imageUrl)
                }
            }
    }
}