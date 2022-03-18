package xyz.teamgravity.calendarintegration

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import xyz.teamgravity.calendarintegration.databinding.ActivityMainBinding

class Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var launcher: ActivityResultLauncher<String>
    private lateinit var resolver: EventContentResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateInIt()
        launcher()
        updateUI()
    }

    private fun lateInIt() {
        resolver = EventContentResolver(this)
    }

    private fun launcher() {
        launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            updateUI()
        }
    }

    private fun updateUI() {
        if (!checkPermission()) return

        binding.apply {

        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, EventContentResolver.PERMISSION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        launcher.launch(EventContentResolver.PERMISSION)
    }

    override fun onResume() {
        super.onResume()
        if (!checkPermission()) requestPermission()
    }
}