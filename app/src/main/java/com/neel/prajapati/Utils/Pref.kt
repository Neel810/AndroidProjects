package com.neel.prajapati.Utils

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.mysocietyapp.watchman.Util.Util.PREF_FILE

object Pref {
    @Nullable
    private var sharedPreferences: SharedPreferences? = null
    fun openPref(@NonNull context: Context) {
        sharedPreferences =
            context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
    }


    @Nullable
    fun getValue(
        @NonNull context: Context,
        key: String?,
        defaultValue: String?
    ): String {
        openPref(context)
        val result: String = sharedPreferences!!.getString(key, defaultValue).toString()
        sharedPreferences = null
        return result
    }
    fun setValue(
        @NonNull context: Context,
        key: String?,
        value: String?
    ) {
        openPref(context)
        var prefsPrivateEditor: SharedPreferences.Editor? = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putString(key, value)
        prefsPrivateEditor!!.commit()
        prefsPrivateEditor = null
        sharedPreferences = null
    }
}