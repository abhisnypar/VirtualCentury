package cricket.ib.virtualcentury.dagger.component

import cricket.ib.virtualcentury.dagger.PresenterModule
import cricket.ib.virtualcentury.dagger.module.AppModule
import cricket.ib.virtualcentury.login.signup.SignupFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (PresenterModule::class)
])
interface AppComponent {

    fun inject(singupFragment: SignupFragment)
}