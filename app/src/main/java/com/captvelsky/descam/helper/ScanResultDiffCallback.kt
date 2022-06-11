package com.captvelsky.descam.helper

import androidx.recyclerview.widget.DiffUtil
import com.captvelsky.descam.data.local.database.ScanResultLocalObject

class ScanResultDiffCallback
    (private val mOldScanResultList: List<ScanResultLocalObject>, private val mNewScanResultList: List<ScanResultLocalObject>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldScanResultList.size
    }

    override fun getNewListSize(): Int {
        return mNewScanResultList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldScanResultList[oldItemPosition].id == mNewScanResultList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldScanResult = mOldScanResultList[oldItemPosition]
        val newScanResult = mNewScanResultList[newItemPosition]
        return oldScanResult.email == newScanResult.email
                && oldScanResult.text == newScanResult.text
                && oldScanResult.result == newScanResult.result
    }

}