package cricket.ib.virtualcentury


import android.support.multidex.MultiDexApplication
import com.facebook.FacebookSdk
import com.nigelbrown.fluxion.Flux
import cricket.ib.virtualcentury.dagger.component.AppComponent
import cricket.ib.virtualcentury.dagger.component.DaggerAppComponent
import cricket.ib.virtualcentury.dagger.module.AppModule

class Application : MultiDexApplication() {

    lateinit var virutalCenturyComponent: AppComponent

    override fun onCreate() {
        FacebookSdk.sdkInitialize(this)
        Flux.init(this)
        virutalCenturyComponent = initDagger(this)
        super.onCreate()

    }

    fun initDagger(app: Application): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
}
