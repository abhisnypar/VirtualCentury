package cricket.ib.virtualcentury.dagger.module

import android.content.Context
import cricket.ib.virtualcentury.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApp():Context = app
}