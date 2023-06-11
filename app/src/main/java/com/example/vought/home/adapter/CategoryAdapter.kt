package com.example.vought.home.adapter

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.vought.R
import com.example.vought.model.CategoryEvent
import kotlinx.android.synthetic.main.res_item_category.view.img_category
import kotlinx.android.synthetic.main.res_item_category.view.txt_title_category

class CategoryAdapter :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items : List<CategoryEvent> = ArrayList()
    private var clickListener: ((CategoryEvent) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return categoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.res_item_category, parent, false)
        )
    }

    fun setOnItemClickListener(listener: (CategoryEvent) -> Unit) {
        clickListener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is categoryViewHolder ->{
                val category = items[position]
                holder.bind(category)
                holder.itemView.setOnClickListener {
                    clickListener?.invoke(category)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setDataSet(categorys: List<CategoryEvent>){
        this.items = categorys
    }

    class categoryViewHolder constructor(
        itemView : View
    ): RecyclerView.ViewHolder(itemView){
        private val categoryImage = itemView.img_category
        private val categoryTitle = itemView.txt_title_category


        fun bind(category: CategoryEvent){
            categoryTitle.text = category.title
            categoryImage.setImageResource(category.imageResId)
        }
    }
}