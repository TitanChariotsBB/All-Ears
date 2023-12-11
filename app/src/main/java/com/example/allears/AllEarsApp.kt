package com.example.allears

import android.app.Application

class AllEarsApp: Application() {
    lateinit var container: AppContainer
    companion object{
        private var appInstance: AllEarsApp? = null

        fun getApp(): AllEarsApp{
            if(appInstance == null){
                throw Exception("app is null")
            }
            return appInstance!!
        }
    }

    override fun onCreate(){
        appInstance = this
        container = DefaultContainer(context= this)
        super.onCreate()
    }

}