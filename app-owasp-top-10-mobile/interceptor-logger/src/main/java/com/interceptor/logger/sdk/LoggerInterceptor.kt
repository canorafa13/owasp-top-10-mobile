package com.interceptor.logger.sdk

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.interceptor.logger.sdk.apis.MallwareApi
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class LoggerInterceptor(private val context: Context): Interceptor {

    @SuppressLint("SimpleDateFormat")
    override fun intercept(chain: Interceptor.Chain): Response {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        val startDate = sdf.format(Date())



        val request = chain.request()
        val url = request.url()
        val reqBody = request.body()
        val method = request.method()
        val reqHeaders = request.headers()


        Log.i("Logger", "Request time :::: $startDate")
        Log.i("Logger", "$method --> $url")
        for (header in reqHeaders.names()) {
            Log.i("Logger", "$header: ${reqHeaders.get(header)}")
        }


        reqBody?.let {
            Log.i("Logger", bodyToString(reqBody))
        }

        val response = chain.proceed(chain.request())

        val endDate = sdf.format(Date())


        val resHeaders = response.headers()
        val statusCode = response.code()

        Log.i("Logger", "Response time :::: $endDate")
        Log.i("Logger", "$statusCode --> $url")
        for (header in resHeaders.names()) {
            Log.i("Logger", "$header: ${resHeaders.get(header)}")
        }

        try {
            Log.i("Logger", response.peekBody(Long.MAX_VALUE).string())
        } catch (e: Exception){
            Log.i("Logger", e.message ?: "")
        }

        MallwareApi.sendInfo(
            context,
            method,
            url.url().toString(),
            reqHeaders,
            reqBody?.let { bodyToString(reqBody) } ?: "",
            resHeaders,
            response.peekBody(Long.MAX_VALUE).string()
        )

        return response
    }

    private fun bodyToString(request: RequestBody): String {
        try {
            val copy = request
            val buffer = Buffer()
            copy.writeTo(buffer)
            return buffer.readUtf8().also {
                buffer.clear()
                buffer.close()
            }
        } catch (e: IOException) {
            return "did not work"
        }
    }
}