package com.example.ricmasters

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class RicMastersApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
