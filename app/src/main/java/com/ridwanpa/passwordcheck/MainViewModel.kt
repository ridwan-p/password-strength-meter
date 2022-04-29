package com.ridwanpa.passwordcheck

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _strength = MutableStateFlow(Strength.VERY_WEAK)
    val strength = _strength.asStateFlow()

    private val passwordMeter = PasswordStrength("")

    fun changePassword  (password:String) {
        _password.value = password
        calculate(password)
    }

    private fun calculate(password:String) {
        passwordMeter.password = password
        this._score.value = passwordMeter.score
        this._strength.value = passwordMeter.strength
    }
}