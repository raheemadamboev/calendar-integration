package xyz.teamgravity.calendarintegration

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.teamgravity.calendarintegration.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resolver = EventContentResolver(this)

        val events = resolver.getEvents()

        for (event in events) {
            raheem("${event.title}, ${event.description}, ${Date(event.startDate)}")
        }
    }
}