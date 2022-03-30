package app.parkee.moviedb.application

import android.app.Application
import app.parkee.moviedb.utils.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
    var mLog:Boolean = false

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}