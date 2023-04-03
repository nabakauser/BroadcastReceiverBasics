package com.example.broadcastrecieversdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.telephony.TelephonyManager
import android.view.Gravity
import android.widget.Toast

class AirplaneModeListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
        if(isAirplaneModeEnabled) {
            Toast.makeText(context, "Airplane mode is enabled", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "Airplane mode is disabled", Toast.LENGTH_SHORT).show()

        if (Intent.ACTION_POWER_CONNECTED == intent.action) {
            Toast.makeText(context, "Power is Connected", Toast.LENGTH_SHORT).show();
        }
        if (Intent.ACTION_POWER_DISCONNECTED == intent.action) {
            Toast.makeText(context, "Power is Disconnected", Toast.LENGTH_SHORT).show();
        }

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_RINGING) {
            val toast = Toast.makeText(context, "Incoming Call", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 250)
            toast.show()
        }
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_IDLE) {
            val toast = Toast.makeText(context, "Call Disconnected", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 250)
            toast.show()
        }
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_OFFHOOK) {
            val toast = Toast.makeText(context, "Call Started", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 250)
            toast.show()
        }
    }
}