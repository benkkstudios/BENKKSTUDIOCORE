package com.benkkstudio.core.databases.security

import android.annotation.SuppressLint
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Suppress("SpellCheckingInspection")
object Encryption {
    fun generateKey(key: String): SecretKey {
        return when (key.length) {
            0 -> throw RuntimeException("empty key")
            32 -> SecretKeySpec(key.toByteArray(), "AES")
            in 1..32 -> generateKey(key + key)
            else -> generateKey(key.substring(0, 32))
        }
    }

    @SuppressLint("GetInstance")
    fun encrypt(message: String, secret: SecretKey): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secret)
        return cipher.doFinal(message.toByteArray())
    }


    @SuppressLint("GetInstance")
    fun decrypt(cipherText: ByteArray?, secret: SecretKey): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secret)
        return String(cipher.doFinal(cipherText))
    }
}