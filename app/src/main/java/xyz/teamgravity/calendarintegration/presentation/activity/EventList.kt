package xyz.teamgravity.calendarintegration.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.teamgravity.calendarintegration.databinding.ActivityEventsBinding

class EventList : AppCompatActivity() {

    private lateinit var binding: ActivityEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}