package com.makhalibagas.topstory.data.source.local

import android.content.SharedPreferences
import com.makhalibagas.topstory.BuildConfig
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class SharedPref @Inject constructor(private val sharedPreferences: SharedPreferences) {

    private val sharedPreferencesEditor = sharedPreferences.edit()

    enum class Key {
        FAV_CLICKED
    }

    fun put(key: Key, value: String) {
        sharedPreferencesEditor.putString(key.name, value).commit()
    }

    fun getString(key: Key, defaultValue: String = "b"): String {
        return sharedPreferences.getString(key.name, defaultValue)!!
    }

    companion object {
        const val SESSION_NAME = BuildConfig.APPLICATION_ID + ".pref"
    }
}