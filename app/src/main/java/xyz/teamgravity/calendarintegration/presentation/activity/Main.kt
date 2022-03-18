package xyz.teamgravity.calendarintegration.presentation.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.calendarintegration.R
import xyz.teamgravity.calendarintegration.core.constant.Extra
import xyz.teamgravity.calendarintegration.core.resolver.EventContentResolver
import xyz.teamgravity.calendarintegration.databinding.ActivityMainBinding
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var resolver: EventContentResolver

    private lateinit var launcher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher()
        updateUI()
        button()
    }

    private fun launcher() {
        launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) updateUI()
        }
    }

    private fun updateUI() {
        binding.apply {
            if (!checkPermission()) return

            calendar.setEvents(buildCalendarEvents())
        }
    }

    private fun button() {
        onCalendar()
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, EventContentResolver.PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        launcher.launch(EventContentResolver.PERMISSION)
    }

    private fun buildCalendarEvents(): List<EventDay> {
        return resolver.getEvents().map {
            EventDay(
                day = Calendar.getInstance().apply { timeInMillis = it.startDate },
                drawableRes = R.drawable.ic_event
            )
        }
    }

    private fun onCalendar() {
        binding.calendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val intent = Intent(this@Main, EventList::class.java)
                intent.putExtra(Extra.SELECTED_TIME, eventDay.calendar.timeInMillis)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!checkPermission()) requestPermission()
    }
}