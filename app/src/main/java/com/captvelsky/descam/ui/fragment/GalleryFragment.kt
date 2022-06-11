package com.captvelsky.descam.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.util.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.captvelsky.descam.R
import com.captvelsky.descam.data.local.database.ScanResultLocalObject
import com.captvelsky.descam.databinding.FragmentGalleryBinding
import com.captvelsky.descam.helper.uriToFile
import com.captvelsky.descam.ui.model.GalleryViewModel
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.lang.StringBuilder

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding

    private val viewModel: GalleryViewModel by viewModels()
    private var getFile: File? = null
    private var scanResult: ScanResultLocalObject? = null

    private lateinit var bitmap: Bitmap
    private lateinit var text: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButon()
    }

    private fun setupButon() {
        binding?.openGalleryButton?.setOnClickListener { openGallery() }
        binding?.uploadButtonImageView?.setOnClickListener { uploadPicture() }
        binding?.extractTextButton?.setOnClickListener { extractText(bitmap) }
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
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myImgFile = uriToFile(selectedImg, requireActivity())
            getFile = myImgFile
            bitmap = BitmapFactory.decodeFile(myImgFile.path)

            binding?.previewImageView?.setImageURI(selectedImg)
            adjustButton()
        }
    }

    private fun uploadPicture() {
        // Fungsi upload diletakkan di sini
        Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()

        sendResultToLocalDatabase(/*String hasil response*/)
    }

    private fun sendResultToLocalDatabase(/*result: String*/) {
        // Fungsi setelah mendapatkan response dari server diletakkan di sini
        viewModel.viewModelScope.launch {
            viewModel.getUserEmail().collect { email ->
                scanResult.let { scanResult ->
                    scanResult?.email = email!!
                    scanResult?.text = text
                    //scanResult?.result = result
                }
                viewModel.insert(scanResult as ScanResultLocalObject)
                Toast.makeText(requireActivity(), resources.getString(R.string.result_added), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun extractText(bitmap: Bitmap) {
        val textRecognizer = TextRecognizer.Builder(requireActivity()).build()
        if (!textRecognizer.isOperational) {
            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
        } else {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val textBlockSparseArray = textRecognizer.detect(frame)
            val stringBuilder = StringBuilder()
            textBlockSparseArray.forEach { _, value ->
                val textBlock = value
                stringBuilder.append(textBlock.value)
                stringBuilder.append("\n")
            }
            text = stringBuilder.toString()
            binding?.extractedTextTextView?.text = stringBuilder.toString()
            binding?.uploadButtonImageView?.visibility = View.VISIBLE
        }
    }

    private fun adjustButton() {
        if (getFile == null) {
            binding?.extractTextButton?.visibility = View.GONE
            binding?.extractedTextCardView?.visibility = View.GONE
        } else {
            binding?.openGalleryButton?.text = resources.getString(R.string.choose_another_picture)
            binding?.extractTextButton?.visibility = View.VISIBLE
            binding?.extractedTextCardView?.visibility = View.VISIBLE
        }
    }
}