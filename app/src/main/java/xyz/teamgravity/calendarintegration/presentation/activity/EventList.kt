package xyz.teamgravity.calendarintegration.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.teamgravity.calendarintegration.databinding.ActivityEventListBinding

class EventList : AppCompatActivity() {

    private lateinit var binding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}