package cricket.ib.virtualcentury.dagger

import cricket.ib.virtualcentury.login.FirebaseLoginFlow
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PresenterModule{

    @Provides
    @Singleton
    fun provideLoginFlowPresenter(): FirebaseLoginFlow = FirebaseLoginFlow()
}