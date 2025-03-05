package ag.sokolov.telepager

import ag.sokolov.telepager.feature.home.di.homeModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                homeModule
            )
        }
    }
}
