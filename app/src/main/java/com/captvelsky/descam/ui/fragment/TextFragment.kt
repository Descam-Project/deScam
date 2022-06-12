package com.captvelsky.descam.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.captvelsky.descam.R
import com.captvelsky.descam.data.local.database.ScanResultLocalObject
import com.captvelsky.descam.databinding.FragmentTextBinding
import com.captvelsky.descam.ui.model.TextViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TextFragment : Fragment() {

    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding
    private val viewModel: TextViewModel by viewModels()

    private var scanResult: ScanResultLocalObject? = null

    private lateinit var text: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.uploadButtonImageView?.setOnClickListener { uploadTextToModel() }
    }

    private fun uploadTextToModel() {
        if (binding?.textInputEditText?.text.toString() != "") {
            text = binding?.textInputEditText?.text.toString()
            viewModel.viewModelScope.launch {
                viewModel.sendTextToModel(text).collect { response ->
                    response.onSuccess {
                        val result = it.output

                        viewModel.getUserEmail().collect { email ->
                            viewModel.sendResultToDatabase(email.toString(), text, result).collect {
                                response.onSuccess {
                                    sendResultToLocalDatabase(result)
                                }

                                response.onFailure {
                                    Toast.makeText(
                                        requireActivity(),
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    response.onFailure {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                        Log.e("Error", it.message.toString())
                    }
                }
            }
        } else {
            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.empty_input_warning),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun sendResultToLocalDatabase(result: String) {
        viewModel.viewModelScope.launch {
            viewModel.getUserEmail().collect { email ->
                scanResult = ScanResultLocalObject()
                scanResult.let { scanResult ->
                    scanResult?.email = email
                    scanResult?.text = text
                    scanResult?.result = result
                }
                viewModel.insertToLocalDatabase(scanResult as ScanResultLocalObject)
                Toast.makeText(
                    requireActivity(),
                    resources.getString(R.string.result_added),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}