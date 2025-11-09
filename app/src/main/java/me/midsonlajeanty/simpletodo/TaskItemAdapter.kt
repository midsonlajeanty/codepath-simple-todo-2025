package me.midsonlajeanty.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter(private val listOfItems: List<String>, val longClickListener: OnLongClickListener)
    : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val taskView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listOfItems[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(bindingAdapterPosition)
                true
            }
        }
    }
}