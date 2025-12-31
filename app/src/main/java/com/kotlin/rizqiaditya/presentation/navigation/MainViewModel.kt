package com.kotlin.rizqiaditya.presentation.main

import androidx.lifecycle.ViewModel
import com.kotlin.rizqiaditya.presentation.navigation.MainDestination
import com.kotlin.rizqiaditya.presentation.navigation.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    fun setDestination(destination: MainDestination) {
        _state.value = _state.value.copy(
            currentDestination = destination
        )
    }
}