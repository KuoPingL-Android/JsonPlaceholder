package studio.saladjam.jsonplaceholder

import android.app.Application
import kotlin.properties.Delegates

class JSONPlaceholderApplication : Application() {
    companion object {
        var INSTANCE by Delegates.notNull<JSONPlaceholderApplication>()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}