package xyz.teamgravity.calendarintegration.presentation.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.teamgravity.calendarintegration.R
import xyz.teamgravity.calendarintegration.core.constant.Extra
import xyz.teamgravity.calendarintegration.data.resolver.EventContentResolver
import xyz.teamgravity.calendarintegration.data.model.EventModel
import xyz.teamgravity.calendarintegration.databinding.ActivityEventCalendarBinding
import xyz.teamgravity.calendarintegration.presentation.viewmodel.EventCalendarViewModel
import java.util.*

@AndroidEntryPoint
class EventCalendar : AppCompatActivity() {

    private lateinit var binding: ActivityEventCalendarBinding

    private val viewmodel by viewModels<EventCalendarViewModel>()

    private lateinit var launcher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher()
        button()
        observe()
    }

    private fun launcher() {
        launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (granted.all { true }) observe()
        }
    }

    private fun button() {
        onCalendar()
    }

    private fun observe() {
        if (!checkPermission()) return
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                updateUI(viewmodel.events.first().toList())
            }
        }
    }

    private fun updateUI(events: List<EventModel>) {
        binding.calendar.setEvents(
            events.map {
                EventDay(
                    day = Calendar.getInstance().apply { timeInMillis = it.startDate },
                    drawableRes = R.drawable.ic_event
                )
            }
        )
    }

    private fun checkPermission(): Boolean {
        return EventContentResolver.PERMISSION.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
    }

    private fun requestPermission() {
        launcher.launch(EventContentResolver.PERMISSION)
    }

    private fun onCalendar() {
        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val intent = Intent(this@EventCalendar, EventList::class.java)
                intent.putExtra(Extra.SELECTED_TIME, eventDay.calendar.timeInMillis)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!checkPermission()) requestPermission()
    }
}