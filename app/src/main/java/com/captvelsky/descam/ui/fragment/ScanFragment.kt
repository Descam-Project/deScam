package com.captvelsky.descam.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.captvelsky.descam.R
import com.captvelsky.descam.databinding.FragmentScanBinding
import com.captvelsky.descam.helper.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding
    private var getFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButon()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setupButon() {
        binding?.openGalleryButton?.setOnClickListener { openGallery() }
        binding?.uploadButtonImageView?.setOnClickListener { uploadPicture() }
    }

    private fun openGallery() {
        val intentToGallery = Intent()
        intentToGallery.action = Intent.ACTION_GET_CONTENT
        intentToGallery.type = "image/*"
        val chooser = Intent.createChooser(intentToGallery, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg : Uri = result.data?.data as Uri
            val myImgFile = uriToFile(selectedImg, requireActivity())
            getFile = myImgFile

            binding?.previewImageView?.setImageURI(selectedImg)
            adjustButton()
        }
    }

    private fun uploadPicture() {
        // Fungsi upload diletakkan di sini
    }

    private fun adjustButton() {
        if (getFile == null) {
            binding?.uploadButtonImageView?.visibility = View.GONE
        } else {
            binding?.openGalleryButton?.text = resources.getString(R.string.choose_another_picture)
            binding?.uploadButtonImageView?.visibility = View.VISIBLE
        }
    }
}