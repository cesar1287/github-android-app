package comcesar1287.github.githubapp

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)
    }
}