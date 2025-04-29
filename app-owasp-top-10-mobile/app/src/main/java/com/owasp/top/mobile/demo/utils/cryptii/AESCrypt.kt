package com.owasp.top.mobile.demo.utils.cryptii

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64
import javax.crypto.spec.IvParameterSpec

object AESCrypt {

    fun encrypt(plainText: String, key: ByteArray, iv: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
    }

    fun decrypt(cipherText: String, key: ByteArray, iv: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decryptedBytes = cipher.doFinal(Base64.decode(cipherText, Base64.NO_WRAP))
        return String(decryptedBytes, Charsets.UTF_8)
    }
}