package com.captvelsky.descam.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.captvelsky.descam.adapter.ScanResultAdapter
import com.captvelsky.descam.databinding.FragmentResultBinding
import com.captvelsky.descam.ui.model.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding
    private val viewModel: ResultViewModel by viewModels()

    private lateinit var adapter: ScanResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllScanResult().observe(requireActivity(), { scanResultList ->
            if (scanResultList != null){
                adapter.setListScanResults(scanResultList)
            }
        })

        adapter = ScanResultAdapter()

        binding?.scanResultRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.scanResultRecyclerView?.setHasFixedSize(true)
        binding?.scanResultRecyclerView?.adapter = adapter
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}