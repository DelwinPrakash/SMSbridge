package com.example.smsbridge

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {

    private lateinit var server: SmsServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS),
            1
        )

        server = SmsServer(8080)
        server.start()

        println("Server started on port 8080")
    }

    override fun onDestroy() {
        super.onDestroy()
        server.stop()
    }
}