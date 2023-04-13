package com.example.ricmasters

import android.app.Application
import io.realm.Realm

class RicMastersApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
