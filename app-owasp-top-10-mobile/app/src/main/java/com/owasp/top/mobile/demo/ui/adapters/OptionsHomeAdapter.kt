package com.owasp.top.mobile.demo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.owasp.top.mobile.demo.databinding.LayoutOptionsBinding

class OptionsHomeAdapter (
    private val items: List<OptionsItemHome>
) : RecyclerView.Adapter<OptionsHomeAdapter.ViewHolder>() {

    class ViewHolder(private val binding: LayoutOptionsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OptionsItemHome) {
            binding.title.setText(item.title)
            binding.action.setOnClickListener {
                item.action.invoke()
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding = LayoutOptionsBinding
            .inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(items[p1])
    }
}

data class OptionsItemHome(
    val title: String,
    val action: () -> Unit
)