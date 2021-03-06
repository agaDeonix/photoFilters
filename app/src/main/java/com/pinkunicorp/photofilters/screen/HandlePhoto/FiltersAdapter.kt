package com.pinkunicorp.photofilters.screen.HandlePhoto

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pinkunicorp.photofilters.R
import com.pinkunicorp.photofilters.filters.IFilter


class FiltersAdapter(var filtersList: List<IFilter>, var selectListener: FilterSelectListener?, var context: Context?) : RecyclerView.Adapter<FiltersAdapter.FilterViewHolder>(){

    interface FilterSelectListener {
        fun select(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        //inflate the layout file
        val filterView = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        val gvh = FilterViewHolder(filterView)
        return gvh
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.mTextView.setText(context?.getString(filtersList.get(position).nameResId))
        filtersList.get(position).applyFilter(holder.mImageView)
        holder.itemView.setOnClickListener{
            selectListener?.select(position)
        }
    }

    override fun getItemCount(): Int {
        return filtersList.size
    }

    class FilterViewHolder : RecyclerView.ViewHolder {
        var mImageView: ImageView
        var mTextView: TextView
        constructor(view: View):super(view){
            mImageView=view.findViewById(R.id.ivTemplate);
            mTextView=view.findViewById(R.id.tvName);
        }
    }
}