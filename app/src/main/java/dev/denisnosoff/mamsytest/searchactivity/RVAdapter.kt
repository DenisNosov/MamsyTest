package dev.denisnosoff.mamsytest.searchactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.denisnosoff.mamsytest.R
import dev.denisnosoff.mamsytest.model.cities.CityItem
import kotlinx.android.synthetic.main.view_item.view.*

class RVAdapter(
    var stringList: List<CityItem>,
    private val listener: (CityItem) -> (Unit)
) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false))
    }

    override fun getItemCount() = stringList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stringList[position], listener)
    }

    fun refreshItems(list: List<CityItem>) {
        stringList = list
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CityItem, listener: (CityItem) -> Unit) = with(itemView) {
            tvCityInfo.text = item.name
            setOnClickListener { listener(item) }
        }
    }
}