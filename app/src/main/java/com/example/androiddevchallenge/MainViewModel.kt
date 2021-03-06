package com.example.androiddevchallenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var isCounting = false
        private set

    var totalCountdownTime: Int by mutableStateOf(value = 0)

    var currentCountdownTime: Int by mutableStateOf(value = 0)

    fun startCountdown(countdownTime: Int) {
        isCounting = true
        totalCountdownTime = countdownTime

        viewModelScope.launch(Dispatchers.Main) {
            val tickSeconds = 0
            for (second in totalCountdownTime downTo tickSeconds) {
                if (!isActive) return@launch

                currentCountdownTime = totalCountdownTime - second
                if (currentCountdownTime != 0) {
                    delay(1000L)
                }
            }
        }
    }

    fun resetCountdown() {
        isCounting = false
    }
}