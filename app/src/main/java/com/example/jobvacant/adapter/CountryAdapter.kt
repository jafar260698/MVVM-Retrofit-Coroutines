package com.example.jobvacant.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.jobvacant.R
import com.example.jobvacant.model.Countries
import kotlinx.android.synthetic.main.item.view.*

class CountryAdapter(var context: Context, var list: List<Countries>, var listener: OnItemClickListener)
    :RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
       return CountryViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val countries:Countries=list[position]
        holder.country_name.text=countries.country
        holder.poytaxt_textview.text=countries.capital
        // it is
        val requestOptions = RequestOptions()
            .placeholder(R.color.card1)
            .error(R.color.card1)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()

        Glide.with(context)
            .applyDefaultRequestOptions(requestOptions)
            .load(Uri.parse(countries.image))
            //.transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.country_image)

        holder.initialize(list[position],listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(listRestaurant: List<Countries>){
        val oldItems=list
        val diffResult: DiffUtil.DiffResult= DiffUtil.calculateDiff(
            RestaurantItemDiffCalback(oldItems,listRestaurant))

        list=listRestaurant
        diffResult.dispatchUpdatesTo(this)
    }

    class RestaurantItemDiffCalback(
        var oldItems :List<Countries>,
        var newItems:List<Countries>
    ): DiffUtil.Callback(){
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldItems[oldItemPosition].country==newItems.get(oldItemPosition).country)
        }

        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition))
        }
    }

    class CountryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
       val country_name=itemView.country_name
       val poytaxt_textview=itemView.poytaxt_textview
       val country_image=itemView.country_image

        fun initialize(item:Countries,action:OnItemClickListener){
            itemView.setOnClickListener {
                action.onItemClick(item,adapterPosition)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(item: Countries, position: Int)
    }

}