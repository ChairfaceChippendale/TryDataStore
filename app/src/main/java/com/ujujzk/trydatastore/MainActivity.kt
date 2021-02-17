package com.ujujzk.trydatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.createDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val store = createDataStore(
            fileName = "auth.pb",
            serializer = AuthSerializer()
        )

        lifecycleScope.launch {

            store.updateData {
                it.toBuilder()
                    .setAuthToken("Hello Auth")
                    .setRefreshToken("Hello Refresh")
                    .build()
            }

            store.data
                .catch { ex ->
                    if (ex is IOException){
                        emit(Auth.getDefaultInstance())
                    } else {
                        throw ex
                    }
                }.collect {
                    Log.e("FFF", "${it.authToken} ${it.refreshToken}")
                }

        }

        findViewById<View>(R.id.ffff).setOnClickListener {
            lifecycleScope.launch {
                store.updateData {
                    it.toBuilder()
                        .setAuthToken("Goodbye Auth")
                        .setRefreshToken("Goodbye Refresh")
                        .build()
                }
            }
        }

    }
}