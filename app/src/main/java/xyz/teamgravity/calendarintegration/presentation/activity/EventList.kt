package xyz.teamgravity.calendarintegration.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
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

    private var selectedTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateInIt()
        button()
        updateUI()
        observe()
    }

    private fun lateInIt() {
        selectedTime = intent.getLongExtra(Extra.SELECTED_TIME, System.currentTimeMillis())
    }

    private fun button() {
        onBack()
        onAdd()
    }

    private fun updateUI() {
        binding.headerT.text = formatter.format(selectedTime)
        recyclerview()
    }

    private fun recyclerview() {
        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val data = viewmodel.events.first().toList()
                adapter.submitList(data)
                hideEmptyLayout(data.size)
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

    private fun onAdd() {
        binding.addB.setOnClickListener {
            val intent = Intent(this, EventAdd::class.java)
            intent.putExtra(Extra.SELECTED_TIME, selectedTime)
            startActivity(intent)
        }
    }
}