package com.tecsup.tecsupapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tecsup.lab11maps.Place

class PlaceAdapter(private val places: List<Place>, private val onClickListener: (Place) -> Unit) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.placeCardImage)
        private val textView: TextView = itemView.findViewById(R.id.placeName)
        private val textView2: TextView= itemView.findViewById(R.id.placeDescription)

        fun bind(place: Place) {
            imageView.setImageResource(place.imageResId)
            textView.text = place.name
            textView2.text = place.description
            itemView.setOnClickListener { onClickListener(place) }
        }
    }
}
