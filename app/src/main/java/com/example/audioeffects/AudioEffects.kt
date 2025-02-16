package com.example.audioeffects

import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow

class AudioEffects: Application() {

    val audioSessionId = MutableStateFlow<Int?>(null)
}