package xyz.teamgravity.calendarintegration.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import xyz.teamgravity.calendarintegration.presentation.adapter.EventListAdapter
import java.text.SimpleDateFormat

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun provideEventListDiff(): EventListAdapter.EventListDiff = EventListAdapter.EventListDiff()

    @Provides
    @ActivityScoped
    fun provideEventListAdapter(
        diff: EventListAdapter.EventListDiff,
        @FullTimeFormatter formatter: SimpleDateFormat
    ) = EventListAdapter(diff = diff, formatter = formatter)
}