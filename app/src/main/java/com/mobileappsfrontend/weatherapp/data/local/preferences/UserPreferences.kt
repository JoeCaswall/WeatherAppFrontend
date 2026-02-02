package com.mobileappsfrontend.weatherapp.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

// This stores the JWT securely using DataStore after login.
class UserPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        val JWT = stringPreferencesKey("jwt")
    }

    suspend fun saveJwt(token: String) {
        dataStore.edit { it[JWT] = token }
        println("UserPreferences: saved JWT")
    }

    val jwtFlow = dataStore.data.map {
        println("UserPreferences: emitting JWT = ${it[JWT]}")
        it[JWT]
    }

}
