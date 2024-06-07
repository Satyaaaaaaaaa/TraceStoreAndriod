package com.sutonglabs.tracestore.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

suspend fun saveJwtToken(context: Context, token: String) {
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.JWT_TOKEN] = token
    }
}

fun getJwtToken(context: Context): Flow<String?> {
    return context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.JWT_TOKEN]
        }
}