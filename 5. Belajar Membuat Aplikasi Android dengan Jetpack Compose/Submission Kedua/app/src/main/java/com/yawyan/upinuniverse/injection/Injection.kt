package com.yawyan.upinuniverse.injection

import com.yawyan.upinuniverse.data.UpinIpinRepository

object Injection {
    fun provideRepository(): UpinIpinRepository {
        return UpinIpinRepository.getInstance()
    }
}