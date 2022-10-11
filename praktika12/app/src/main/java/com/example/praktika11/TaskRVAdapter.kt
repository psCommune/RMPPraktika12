package com.example.praktika11

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessControlContext

class TaskRVAdapter (context: Context?, val data: MutableList<Tasks>): RecyclerView.Adapter<TaskRVAdapter.TaskViewHolder?>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var iClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = layoutInflater.inflate(R.layout.item_task,parent,false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = data[position]
        holder.headingTextView.text = item.heading
        holder.meaningTextView.text = item.meaning
        holder.dataTextView.text = item.time

    }

    override fun getItemCount(): Int= data.size

    inner class TaskViewHolder (item: View): RecyclerView.ViewHolder(item), View.OnClickListener {
        var headingTextView: TextView = item.findViewById(R.id.headingTask)
        var meaningTextView: TextView = item.findViewById(R.id.meaningTask)
        var dataTextView: TextView = item.findViewById(R.id.dataTask)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            iClickListener?.onItemClick(view, adapterPosition)
        }

    }

    fun setOnClickListener(itemClickListener: ItemClickListener?){
        iClickListener = itemClickListener
    }
    fun setOnLongClickListener(temClickListener: ItemClickListener?){
    }

    interface ItemClickListener{
        fun onItemClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }


}