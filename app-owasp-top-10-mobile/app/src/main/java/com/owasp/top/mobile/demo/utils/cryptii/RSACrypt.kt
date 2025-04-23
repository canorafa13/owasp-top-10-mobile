package com.owasp.top.mobile.demo.utils.cryptii
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.security.KeyFactory
import android.util.Base64
import javax.crypto.Cipher

object RSACrypt {

    fun encrypt(plainText: String, publicKey: PublicKey): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)

    }

    fun decrypt(cipherText: String, privateKey: PrivateKey): String {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        val encryptedData = Base64.decode(cipherText.toByteArray(), Base64.NO_WRAP)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData)
    }

    fun publicKeyToString(publicKey: PublicKey): String {
        val keySpec = X509EncodedKeySpec(publicKey.encoded)
        return Base64.encodeToString(keySpec.encoded, Base64.NO_WRAP)

    }

    fun privateKeyToString(privateKey: PrivateKey): String {
        val keySpec = PKCS8EncodedKeySpec(privateKey.encoded)
        return Base64.encodeToString(keySpec.encoded, Base64.NO_WRAP)
    }

    fun stringToPublicKey(publicKeyString: String): PublicKey {
        val keySpec = X509EncodedKeySpec(Base64.decode(publicKeyString, Base64.NO_WRAP))
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }

    fun stringToPrivateKey(privateKeyString: String): PrivateKey {
        val keySpec = PKCS8EncodedKeySpec(Base64.decode(privateKeyString, Base64.NO_WRAP))

        val keyFactory = KeyFactory.getInstance("RSA")

        return keyFactory.generatePrivate(keySpec)
    }
}