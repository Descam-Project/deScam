package com.captvelsky.descam.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.captvelsky.descam.R
import com.captvelsky.descam.data.local.database.ScanResultLocalObject
import com.captvelsky.descam.databinding.ItemScanResultBinding
import com.captvelsky.descam.helper.ScanResultDiffCallback

class ScanResultAdapter : RecyclerView.Adapter<ScanResultAdapter.ScanResultViewHolder>() {
    private val listScanResults = ArrayList<ScanResultLocalObject>()

    fun setListScanResults(listScanResults: List<ScanResultLocalObject>) {
        val diffCallback = ScanResultDiffCallback(this.listScanResults, listScanResults)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listScanResults.clear()
        this.listScanResults.addAll(listScanResults)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanResultViewHolder {
        val binding = ItemScanResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScanResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScanResultViewHolder, position: Int) {
        holder.bind(listScanResults[position])
    }

    override fun getItemCount(): Int {
        return listScanResults.size
    }

    inner class ScanResultViewHolder(private val binding: ItemScanResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(scanResult: ScanResultLocalObject) {
            with(binding) {
                emailTextView.text = scanResult.email
                checkedTextTextView.text = scanResult.text
                statusValueTextView.text = scanResult.result
                if (scanResult.result == "legal") {
                    statusValueTextView.setTextColor(Color.parseColor(R.color.legal_color.toString()))
                } else {
                    statusValueTextView.setTextColor(Color.parseColor(R.color.illegal_color.toString()))
                }
            }
        }
    }

}