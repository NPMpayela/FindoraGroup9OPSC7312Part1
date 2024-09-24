package com.example.findoraapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FindoraEventAdapter(private val events: List<Event?>) :
    RecyclerView.Adapter<FindoraEventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImageView: ImageView = view.findViewById(R.id.eventImageView) // Uncommented
        val eventTitleTextView: TextView = view.findViewById(R.id.eventTitleTextView)
        val eventDescriptionTextView: TextView = view.findViewById(R.id.eventDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        if (event != null) {
            holder.eventTitleTextView.text = event.title
        }
        if (event != null) {
            holder.eventDescriptionTextView.text = event.details
        }

//        // Set image if available, otherwise use a placeholder **
//        val bitmap: Bitmap? = event.imageBitmap
//        if (bitmap != null) {
//            holder.eventImageView.setImageBitmap(bitmap)
//        } else {
//            holder.eventImageView.setImageResource(R.drawable.baseline_add_photo_alternate_24_2)
//        }
    }

    override fun getItemCount(): Int {
        return events.size
    }
}
