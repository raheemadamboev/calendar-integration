package xyz.teamgravity.calendarintegration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.teamgravity.calendarintegration.databinding.ActivityEventsBinding

class Events : AppCompatActivity() {

    private lateinit var binding: ActivityEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}