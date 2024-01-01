package com.dicoding.habitapp.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view

        fun bind(pageType: PageType, pageData: Habit) {
            val tvBigtitle = itemView.findViewById<TextView>(R.id.pg_tv_bigtitle)
            val tvTime = itemView.findViewById<TextView>(R.id.pg_tv_time)
            val tvTimePriotity = itemView.findViewById<TextView>(R.id.pg_tv_priority_time)
            val imgPriority = itemView.findViewById<ImageView>(R.id.pg_priority_level)
            val btn = itemView.findViewById<TextView>(R.id.pg_btn)

            tvBigtitle.text = pageData.title
            tvTime.text = pageData.startTime
            tvTimePriotity.text = pageData.minutesFocus.toString()

            when (pageType) {

                PageType.LOW -> {
                    imgPriority.setImageResource(R.drawable.ic_priority_low)
                }

                PageType.MEDIUM -> {
                    imgPriority.setImageResource(R.drawable.ic_priority_medium)
                }


                PageType.HIGH -> {
                    imgPriority.setImageResource(R.drawable.ic_priority_high)
                }
            }

            btn.setOnClickListener {
                onClick(pageData)
            }

        }
    }
}
