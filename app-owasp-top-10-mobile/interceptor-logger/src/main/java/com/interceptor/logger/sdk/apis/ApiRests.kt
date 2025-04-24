package com.interceptor.logger.sdk.apis

import java.net.HttpURLConnection
import java.net.URL
import java.io.OutputStreamWriter
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.*

class ApiRests {

    fun post(apiUrl: String, jsonData: String) {
        println(apiUrl)
        println(jsonData)
        var connection: HttpURLConnection? = null
        val thread = Thread(Runnable {
            try {
                val url = URL(apiUrl)
                connection = url.openConnection() as HttpURLConnection
                connection?.requestMethod = "POST"
                connection?.setRequestProperty("Content-Type", "application/json")
                connection?.setRequestProperty("accept", "application/json")
                connection?.doOutput = true

                val outputStreamWriter = OutputStreamWriter(connection?.outputStream, "UTF-8")
                outputStreamWriter.write(jsonData)
                outputStreamWriter.flush()
                outputStreamWriter.close()


                val responseCode = connection?.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val bufferedReader = BufferedReader(InputStreamReader(connection?.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (bufferedReader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    bufferedReader.close()
                    println(response.toString())
                } else {
                    println(connection?.responseMessage)
                    println("Error: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: ${e.message}")
            } finally {
                connection?.disconnect()
            }
        })

        thread.start()
    }
}