package com.benkkstudio.core.databases.repos

import android.content.Context
import com.benkkstudio.core.databases.security.Encryption
import com.benkkstudio.core.databases.security.Encryption.generateKey
import javax.crypto.SecretKey

class SafeBox(context: Context, key: String) {

    private var secretKey: SecretKey = generateKey(key)
    private val byteRepo: ByteDAO = ByteDAO(context, RootMode.LOCAL)

    fun save(fileName: String, sensitiveData: String) =
        byteRepo.save(
            fileName,
            Encryption.encrypt(sensitiveData, secretKey)
        )

    fun load(fileName: String) =
        Encryption.decrypt(
            byteRepo.load(fileName),
            secretKey
        )
}