package com.ujujzk.trydatastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class AuthSerializer: Serializer<Auth> {

    override val defaultValue: Auth
        get() = Auth.getDefaultInstance()

    override fun readFrom(input: InputStream): Auth {
        return try {
            Auth.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException){
            throw CorruptionException("Error deserializing proto", ex)
        }
    }

    override fun writeTo(t: Auth, output: OutputStream) {
        t.writeTo(output)
    }
}