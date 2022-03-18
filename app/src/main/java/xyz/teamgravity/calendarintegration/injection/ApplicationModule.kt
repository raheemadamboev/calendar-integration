package xyz.teamgravity.calendarintegration.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.calendarintegration.core.resolver.EventContentResolver
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideEventContentResolver(@ApplicationContext context: Context): EventContentResolver = EventContentResolver(context)

    @Provides
    @Singleton
    @FullTimeFormatter
    fun provideFullTimeFormatter(): SimpleDateFormat = SimpleDateFormat("yyyy, MMMM d, HH:mm", Locale.getDefault())
}