package xyz.teamgravity.calendarintegration.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.calendarintegration.core.constant.Extra
import xyz.teamgravity.calendarintegration.core.extension.gone
import xyz.teamgravity.calendarintegration.core.extension.visible
import xyz.teamgravity.calendarintegration.core.util.Time
import xyz.teamgravity.calendarintegration.databinding.ActivityEventListBinding
import xyz.teamgravity.calendarintegration.presentation.adapter.EventListAdapter
import xyz.teamgravity.calendarintegration.presentation.viewmodel.EventListViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class EventList : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding

    private val viewmodel by viewModels<EventListViewModel>()

    @Inject
    lateinit var adapter: EventListAdapter

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
        observe()
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
        binding.headerT.text = formatter.format(intent.getLongExtra(Extra.SELECTED_TIME, System.currentTimeMillis()))
    }

    private fun observe() {
        lifecycleScope.launch {
            viewmodel.events.collectLatest { events ->
                if (events.ready) {
                    adapter.submitList(events.data)
                    hideEmptyLayout(events.data.size)
                }
            }
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