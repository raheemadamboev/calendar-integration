package xyz.teamgravity.calendarintegration.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.teamgravity.calendarintegration.R
import xyz.teamgravity.calendarintegration.core.extension.gone
import xyz.teamgravity.calendarintegration.core.extension.visible
import xyz.teamgravity.calendarintegration.data.model.EventModel
import xyz.teamgravity.calendarintegration.databinding.CardEventBinding
import java.text.SimpleDateFormat

class EventListAdapter(
    diff: EventListDiff,
    private val formatter: SimpleDateFormat
) : ListAdapter<EventModel, EventListAdapter.EventListViewHolder>(diff) {

    class EventListViewHolder(private val binding: CardEventBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: EventModel, formatter: SimpleDateFormat) {
            binding.apply {
                titleT.text = root.context.resources.getString(R.string.your_title, model.title)

                if (model.description.isBlank()) {
                    descriptionT.gone()
                } else {
                    descriptionT.text = root.context.resources.getString(R.string.your_description, model.description)
                    descriptionT.visible()
                }

                startDateT.text = root.context.resources.getString(R.string.your_start_date, formatter.format(model.startDate))
                endDateT.text = root.context.resources.getString(R.string.your_end_date, formatter.format(model.endDate))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        return EventListViewHolder(CardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.bind(getItem(position), formatter)
    }

    class EventListDiff : DiffUtil.ItemCallback<EventModel>() {
        override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem == newItem // TODO there is no id yet FIX IT!!!
        }

        override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
            return oldItem == newItem
        }
    }
}