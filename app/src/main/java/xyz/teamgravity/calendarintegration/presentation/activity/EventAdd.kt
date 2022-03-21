package xyz.teamgravity.calendarintegration.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.calendarintegration.core.extension.clearErrorFocus
import xyz.teamgravity.calendarintegration.core.extension.fieldError
import xyz.teamgravity.calendarintegration.core.extension.text
import xyz.teamgravity.calendarintegration.databinding.ActivityEventAddBinding
import xyz.teamgravity.calendarintegration.presentation.viewmodel.EventAddViewModel

@AndroidEntryPoint
class EventAdd : AppCompatActivity() {

    private lateinit var binding: ActivityEventAddBinding

    private val viewmodel by viewModels<EventAddViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button()
        observe()
    }

    private fun button() {
        onBack()
        onSave()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewmodel.event.collectLatest { event ->
                event(event)
            }
        }
    }

    private fun event(event: EventAddViewModel.EventAddEvent) {
        when (event) {
            EventAddViewModel.EventAddEvent.EventAdded -> {
                finish()
            }
        }
    }

    private fun validateInputs() {
        binding.apply {
            titleField.clearErrorFocus()

            val title = titleField.text()
            val description = descriptionField.text()

            if (title.isEmpty()) {
                titleField.fieldError()
                return
            }

            viewmodel.onAddEvent(title = title, description = description)
        }
    }

    private fun onBack() {
        binding.backB.setOnClickListener {
            onBackPressed()
        }
    }

    private fun onSave() {
        binding.saveB.setOnClickListener {
            validateInputs()
        }
    }
}