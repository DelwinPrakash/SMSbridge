package com.example.smsbridge

import android.telephony.SmsManager

object SmsSender {

    fun sendSms(phone: String, message: String) {
        val smsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(
            phone,
            null,
            message,
            null,
            null
        )
    }
}