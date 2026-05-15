package com.delwin.smsbridge

import android.Manifest
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Collections
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {

    private lateinit var server: SmsServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this)
        textView.text = "Starting server..."
        textView.gravity = Gravity.CENTER

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

            textView.text = "Server running on port 8080\nIP: ${getIPAddress()}\nhttp://${getIPAddress()}:8080/send-sms"

        } catch (e: Exception) {

            textView.text = "Server failed: ${e.message}"
        }
    }

    private fun getIPAddress(): String {
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val sAddr = addr.hostAddress
                        val isIPv4 = sAddr.indexOf(':') < 0
                        if (isIPv4) return sAddr
                    }
                }
            }
        } catch (e: Exception) {
            return "Unknown"
        }
        return "Not found"
    }

    override fun onDestroy() {
        super.onDestroy()

        server.stop()
    }
}