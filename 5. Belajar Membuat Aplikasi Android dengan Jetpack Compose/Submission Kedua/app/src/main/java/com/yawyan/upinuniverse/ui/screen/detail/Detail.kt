package com.yawyan.upinuniverse.ui.screen.detail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yawyan.upinuniverse.data.UpinIpinRepository
import com.yawyan.upinuniverse.model.FavUpin
import com.yawyan.upinuniverse.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Detail(private val repository: UpinIpinRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavUpin>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<FavUpin>> get() = _uiState

    fun getById(UpinId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getById(UpinId))
        }
    }
}