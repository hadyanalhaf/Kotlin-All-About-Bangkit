package com.yawyan.upinuniverse.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yawyan.upinuniverse.data.UpinIpinRepository
import com.yawyan.upinuniverse.model.FavUpin
import com.yawyan.upinuniverse.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class Home(private val repository: UpinIpinRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<FavUpin>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavUpin>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            try {
                val result = repository.searchData(_query.value)
                    .map { data -> data.sortedBy { it.upin.name } }
                    .first()

                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getAllData() {
        viewModelScope.launch {
            repository.getData()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { data ->
                    _uiState.value = UiState.Success(data)

                }
        }
    }
}