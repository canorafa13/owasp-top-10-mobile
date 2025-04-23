package com.owasp.top.mobile.demo.utils


import com.google.gson.Gson
import com.owasp.top.mobile.demo.data.ObjectCryptiiBase
import com.owasp.top.mobile.demo.utils.cryptii.AESCrypt
import com.owasp.top.mobile.demo.utils.cryptii.RSACrypt
import javax.inject.Inject

class HelperCipherApp @Inject constructor(
    val helperSecure: HelperSecure
){
    private val randomPasswordSize = 48
    fun encryptRSAPass(pass: String): String {
        val publicKey = RSACrypt.stringToPublicKey(helperSecure.rsaPublicKey)
        return RSACrypt.encrypt(pass, publicKey)
    }

    fun decryptRSAPass(pass: String): String {
        val privateKey = RSACrypt.stringToPrivateKey(helperSecure.rsaPrivateKey)
        return RSACrypt.decrypt(pass, privateKey)
    }

    fun <T> encryptAESData(data: T): ObjectCryptiiBase {
        val password = randomPasswordSize.getRandomStringBySize().toHex()
        val iv = password.toByteArray(Charsets.UTF_8).copyOfRange(password.length - 16, password.length)
        val key = password.toByteArray(Charsets.UTF_8).copyOf(32)
        return ObjectCryptiiBase(
            data = AESCrypt.encrypt(Gson().toJson(data), key, iv),
            pass = encryptRSAPass(password)
        )
    }


    inline fun <reified T> decryptAESData(p0: ObjectCryptiiBase): T {
        val password = decryptRSAPass(p0.pass)
        val iv = password.toByteArray(Charsets.UTF_8).copyOfRange(password.length - 16, password.length)
        val key = password.toByteArray(Charsets.UTF_8).copyOf(32)
        val jsonDecrypted = AESCrypt.decrypt(p0.data, key, iv)
        return jsonDecrypted.toObject()
    }
}