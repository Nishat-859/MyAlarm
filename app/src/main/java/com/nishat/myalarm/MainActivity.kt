package com.nishat.myalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val outputFile = "${externalCacheDir?.absolutePath}/my_recording.3gp"
            val viewModel: RecorderViewModel = RecorderViewModel(outputFile)
            RecorderScreen(viewModel = viewModel)
        }
    }
}


