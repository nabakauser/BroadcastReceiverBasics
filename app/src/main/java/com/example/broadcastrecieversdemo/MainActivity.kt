package com.example.broadcastrecieversdemo

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    private var receiver: AirplaneModeListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        super.onCreate(savedInstanceState)
        setContent {
            FlashLightDemo(cameraManager = cameraManager)
            //ScreenIcon()
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    777
                )
            }
            receiver = AirplaneModeListener()
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also { intentFilter ->
                registerReceiver(receiver, intentFilter)
            }
            IntentFilter(Intent.ACTION_POWER_CONNECTED).also { intentFilter ->
                registerReceiver(receiver, intentFilter)
            }
            IntentFilter(Intent.ACTION_POWER_DISCONNECTED).also { intentFilter ->
                registerReceiver(receiver, intentFilter)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}

@Composable
fun ScreenIcon() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_broadcast),
            contentDescription = null,
            tint = colorResource(id = R.color.purple)
        )
    }
}

@Composable
fun FlashLightDemo(cameraManager: CameraManager) {
    val rearCamera = cameraManager.cameraIdList[0] //0 for rear camera and 1 for front camera
    val torchState = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Text(
        modifier = Modifier.clickable {
           if(!torchState.value) {
               cameraManager.setTorchMode(rearCamera, true)
               torchState.value = true
               Toast.makeText(context, "Flashlight ON", Toast.LENGTH_SHORT).show()
           } else {
               cameraManager.setTorchMode(rearCamera, false)
               torchState.value = false
               Toast.makeText(context, "Flashlight OFF", Toast.LENGTH_SHORT).show()
           }
        },
        text = "Torch"
    )
}
