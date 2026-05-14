package com.example.smsbridge

import android.Manifest
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {

    private lateinit var server: SmsServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)
        textView.text = "Starting server..."

        setContentView(textView)

        // Request SMS permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS),
            1
        )

        try {

            // Start HTTP server
            server = SmsServer(8080)
            server.start()

            textView.text = "Server running on port 8080"

        } catch (e: Exception) {

            textView.text = "Server failed: ${e.message}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        server.stop()
    }
}