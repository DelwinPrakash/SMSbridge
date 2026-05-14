package com.example.smsbridge

import fi.iki.elonen.NanoHTTPD
import org.json.JSONObject

class SmsServer(port: Int) : NanoHTTPD(port) {

    override fun serve(session: IHTTPSession): Response {

        return try {

            if (session.method == Method.POST &&
                session.uri == "/send-sms") {

                val files = HashMap<String, String>()
                session.parseBody(files)

                val body = files["postData"] ?: ""

                val json = JSONObject(body)

                val phone = json.getString("to")
                val message = json.getString("message")

                SmsSender.sendSms(phone, message)

                newFixedLengthResponse(
                    Response.Status.OK,
                    "application/json",
                    """{"success":true}"""
                )

            } else {

                newFixedLengthResponse(
                    Response.Status.NOT_FOUND,
                    "application/json",
                    """{"error":"Not found"}"""
                )
            }

        } catch (e: Exception) {

            newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                "application/json",
                """{"error":"${e.message}"}"""
            )
        }
    }
}