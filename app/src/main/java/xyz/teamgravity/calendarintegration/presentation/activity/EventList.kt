package xyz.teamgravity.calendarintegration.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.calendarintegration.core.constant.Extra
import xyz.teamgravity.calendarintegration.core.extension.gone
import xyz.teamgravity.calendarintegration.core.extension.visible
import xyz.teamgravity.calendarintegration.core.resolver.EventContentResolver
import xyz.teamgravity.calendarintegration.core.util.Time
import xyz.teamgravity.calendarintegration.databinding.ActivityEventListBinding
import xyz.teamgravity.calendarintegration.presentation.adapter.EventListAdapter
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class EventList : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding

    @Inject
    lateinit var adapter: EventListAdapter

    @Inject
    lateinit var resolver: EventContentResolver

    @Inject
    @Named(Time.ONLY_DATE_FORMATTER)
    lateinit var formatter: SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button()
        recyclerview()
        updateUI()
    }

    private fun button() {
        onBack()
    }

    private fun recyclerview() {
        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter
        }
    }

    private fun updateUI() {
        binding.apply {
            val selectedTime = intent.getLongExtra(Extra.SELECTED_TIME, System.currentTimeMillis())
            headerT.text = formatter.format(selectedTime)

            val events = resolver.getEventsByDate(selectedTime).toList()
            adapter.submitList(events)
            hideEmptyLayout(events.size)
        }
    }

    private fun hideEmptyLayout(count: Int) {
        binding.apply {
            if (count > 0) {
                emptyLayout.gone()
            } else {
                emptyLayout.visible()
            }
        }
    }

    private fun onBack() {
        binding.backB.setOnClickListener {
            onBackPressed()
        }
    }
}