package com.curso.homebakery

import android.app.Application

class App: Application() {
    lateinit var auth: AuthManager

    override fun onCreate() {
        super.onCreate()
        auth = AuthManager(this)

    }
}