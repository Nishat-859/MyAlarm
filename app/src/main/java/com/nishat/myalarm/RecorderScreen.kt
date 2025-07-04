package com.nishat.myalarm

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun RecorderScreen(viewModel: RecorderViewModel) {
    val context = LocalContext.current
    val isRecording by viewModel.isRecording.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                viewModel.startRecording()
                Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    val permissionGranted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED

                    if (permissionGranted) {
                        viewModel.startRecording()
                        Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
                    } else {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                },
                enabled = !isRecording,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Start Recording")
            }

            Button(
                onClick = {
                    viewModel.stopRecording()
                    Toast.makeText(context, "Recording saved:\n${viewModel.getOutputFile()}", Toast.LENGTH_LONG).show()
                },
                enabled = isRecording,
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Stop Recording")
            }
        }
    }
}