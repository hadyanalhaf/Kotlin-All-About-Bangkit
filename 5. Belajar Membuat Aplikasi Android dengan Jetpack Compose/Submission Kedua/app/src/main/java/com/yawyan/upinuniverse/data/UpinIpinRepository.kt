package com.yawyan.upinuniverse.data

import com.yawyan.upinuniverse.model.FavUpin
import com.yawyan.upinuniverse.model.UpinData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UpinIpinRepository {

    private val dummyData = mutableListOf<FavUpin>()

    init {
        if (dummyData.isEmpty()) {
            UpinData.dummyUpin.forEach { data ->
                dummyData.add(FavUpin(data))
            }
        }
    }


    fun getById(UpinId: Long): FavUpin {
        return dummyData.first { data ->
            data.upin.id == UpinId
        }
    }

    fun getData(): Flow<List<FavUpin>> {
        return flowOf(dummyData)
    }


    fun searchData(query: String): Flow<List<FavUpin>> {
        return flowOf(dummyData.filter {
            it.upin.name.contains(query, ignoreCase = true)

        })
    }

    companion object {
        @Volatile
        private var instance: UpinIpinRepository? = null

        fun getInstance(): UpinIpinRepository = instance ?: synchronized(this) {
            UpinIpinRepository().apply {
                instance = this
            }
        }
    }
}