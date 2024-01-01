package com.yawyan.upinipinuniverse

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yawyan.upinipinuniverse.data.UpinIpinRepository
import com.yawyan.upinipinuniverse.model.UpinIpin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UpinIpinUniverseViewModel(private val repository: UpinIpinRepository) : ViewModel() {
    private val _groupedUpinIpin = MutableStateFlow(
        repository.getUpinIpin()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val grupedUpinIpin: StateFlow<Map<Char, List<UpinIpin>>> get() = _groupedUpinIpin

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedUpinIpin.value = repository.searchUpinIpin(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}

class ViewModelFactory(private val repository: UpinIpinRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpinIpinUniverseViewModel::class.java)) {
            return UpinIpinUniverseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}