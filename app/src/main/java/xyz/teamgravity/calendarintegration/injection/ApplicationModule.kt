package xyz.teamgravity.calendarintegration.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.calendarintegration.core.resolver.EventContentResolver
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideEventContentResolver(@ApplicationContext context: Context): EventContentResolver = EventContentResolver(context)
}