package com.owasp.top.mobile.demo.utils.cryptii

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import android.util.Base64
import javax.crypto.spec.IvParameterSpec

object AESCrypt {
    /*private const val AES_ALGORITHM = "AES/GCM/NoPadding"
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = 16
    private const val IV_LENGTH = 12
    private const val TAG_LENGTH = 16
    private const val PBKDF2_ITERATIONS = 65536
    private const val PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256"

    fun encrypt(plainText: String, password: String): String {
        val salt = generateRandomBytes(SALT_LENGTH)
        val iv = generateRandomBytes(IV_LENGTH)
        val secretKey = deriveKey(password, salt)

        val cipher = Cipher.getInstance(AES_ALGORITHM)
        val parameterSpec = GCMParameterSpec(TAG_LENGTH * 8, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec)

        val encryptedBytes = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))

        val byteBuffer = ByteBuffer.allocate(salt.size + iv.size + encryptedBytes.size)
        byteBuffer.put(salt)
        byteBuffer.put(iv)
        byteBuffer.put(encryptedBytes)

        return Base64.encodeToString(byteBuffer.array(), Base64.NO_WRAP)
    }

    fun decrypt(cipherText: String, password: String): String {
        val decodedBytes = Base64.decode(cipherText, Base64.NO_WRAP)
        val byteBuffer = ByteBuffer.wrap(decodedBytes)

        val salt = ByteArray(SALT_LENGTH)
        byteBuffer.get(salt)
        val iv = ByteArray(IV_LENGTH)
        byteBuffer.get(iv)
        val encryptedBytes = ByteArray(byteBuffer.remaining())
        byteBuffer.get(encryptedBytes)

        val secretKey = deriveKey(password, salt)

        val cipher = Cipher.getInstance(AES_ALGORITHM)
        val parameterSpec = GCMParameterSpec(TAG_LENGTH * 8, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, StandardCharsets.UTF_8)
    }

    private fun deriveKey(password: String, salt: ByteArray): SecretKey {
        val factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)
        val spec = PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, KEY_LENGTH)
        val secret = factory.generateSecret(spec)
        return SecretKeySpec(secret.encoded, "AES")
    }

    private fun generateRandomBytes(length: Int): ByteArray {
        val random = SecureRandom()
        val bytes = ByteArray(length)
        random.nextBytes(bytes)
        return bytes
    }
    */

    ///
    /*private const val ALGORITHM = "AES/CBC/PKCS7Padding"
    private const val SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val SALT = "some_salt" // Should be securely generated and stored
    private const val IV_SIZE = 16
    private const val KEY_LENGTH = 256
    private const val ITERATION_COUNT = 65536

    fun encrypt(plainText: String, secretPass: String): String {
        val iv = generateIv()
        val secretKey = generateKeyFromPassword(secretPass, SALT)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
        val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        val ivString = Base64.encodeToString(iv.iv, Base64.NO_WRAP)
        val encryptedString = Base64.encodeToString(encrypted, Base64.NO_WRAP)
        return "$ivString:$encryptedString"
    }

    fun decrypt(cipherText: String, secretPass: String): String {
        val parts = cipherText.split(":")
        val ivString = parts[0]
        val encryptedString = parts[1]

        val iv = IvParameterSpec(Base64.decode(ivString, Base64.NO_WRAP))
        val secretKey = generateKeyFromPassword(secretPass, SALT)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
        val decrypted = cipher.doFinal(Base64.decode(encryptedString, Base64.NO_WRAP))
        return String(decrypted, Charsets.UTF_8)
    }

    private fun generateIv(): IvParameterSpec {
        val iv = ByteArray(IV_SIZE)
        SecureRandom().nextBytes(iv)
        return IvParameterSpec(iv)
    }

    private fun generateKeyFromPassword(password: String, salt: String): SecretKey {
        val factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), ITERATION_COUNT, KEY_LENGTH)
        val secret = factory.generateSecret(spec)
        return SecretKeySpec(secret.encoded, "AES")
    }*/

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