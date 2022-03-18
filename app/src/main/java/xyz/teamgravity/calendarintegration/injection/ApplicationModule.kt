package xyz.teamgravity.calendarintegration.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.calendarintegration.core.resolver.EventContentResolver
import xyz.teamgravity.calendarintegration.core.util.Time
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideEventContentResolver(@ApplicationContext context: Context): EventContentResolver = EventContentResolver(context)

    @Provides
    @Singleton
    @Named(Time.FULL_TIME_FORMATTER)
    fun provideFullTimeFormatter(): SimpleDateFormat = SimpleDateFormat("yyyy, MMMM d, HH:mm", Locale.getDefault())

    @Provides
    @Singleton
    @Named(Time.ONLY_DATE_FORMATTER)
    fun provideOnlyDateFormatter(): SimpleDateFormat = SimpleDateFormat("yyyy, MMMM d", Locale.getDefault())
}