package com.kuanhsien.app.sample.android_recyclerview_demo.ui.discrete_scrollview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuanhsien.app.sample.android_recyclerview_demo.R
import com.kuanhsien.app.sample.android_recyclerview_demo.data.DemoItem
import kotlinx.android.synthetic.main.listitem_discrete_scrollview_item_viewholder.view.*

class DiscreteScrollViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(type: DemoItem)
    }

    private var listener: OnItemClickListener? = null
    private var dataList = mutableListOf<DemoItem>()

    // override to use recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_discrete_scrollview_item_viewholder, parent, false)
        return MainItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainItemViewHolder)
            holder.bind(dataList[position], listener)
    }


    // set Data
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setData(dataList: List<DemoItem>) {
        this.dataList = dataList.toMutableList()
        notifyDataSetChanged()
    }

    fun insertData(position: Int, list: List<DemoItem>) {
        this.dataList.addAll(position, list)
        notifyItemRangeInserted(position, list.size)
    }

    // ViewHolder
    class MainItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: DemoItem, listener: OnItemClickListener?) {

            // set button text
            itemView.btn_item.text = data.id.toString()

            // set button listener
            listener?.run {
                itemView.btn_item.setOnClickListener { onItemClick(data) }
            }
        }
    }
}