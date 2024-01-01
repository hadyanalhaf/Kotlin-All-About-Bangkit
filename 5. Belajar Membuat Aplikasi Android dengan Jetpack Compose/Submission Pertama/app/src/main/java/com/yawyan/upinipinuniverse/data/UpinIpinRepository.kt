package com.yawyan.upinipinuniverse.data

import com.yawyan.upinipinuniverse.model.UpinIpin
import com.yawyan.upinipinuniverse.model.UpinIpinData

class UpinIpinRepository {

    fun getUpinIpin(): List<UpinIpin> {
        return UpinIpinData.UpinIpin
    }

    fun searchUpinIpin(query: String): List<UpinIpin>{
        return UpinIpinData.UpinIpin.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

}