package enxy.githubapitest

import android.app.Application
import enxy.githubapitest.di.appModule
import org.koin.core.context.startKoin

class AndroidApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}