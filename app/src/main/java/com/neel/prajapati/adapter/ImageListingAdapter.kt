package com.neel.prajapati.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.mysocietyapp.watchman.Util.Util.setImageURL
import com.neel.prajapati.R

import com.neel.prajapati.model.ImageListModelItem
import kotlinx.android.synthetic.main.row_image_list.view.*


class ImageListingAdapter(
    private var context: Context?,
    private var imageDataList: ArrayList<ImageListModelItem>,

    private val itemClick: (pos: Int?, ImageListModelItem) -> Unit

) : RecyclerView.Adapter<ImageListingAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.row_image_list, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = imageDataList[position]

        /*Set Title*/
        holder.v.titleTV.text = model.title

        /*Set Thumbnail Image*/
        setImageURL(context!!,model.thumbnailUrl,holder.v.thumbnailIV)

        /*on item click*/
        holder.itemView.setOnClickListener {
            itemClick(position, imageDataList[position])
        }
    }
    override fun getItemCount(): Int { return imageDataList.size }

    class MyViewHolder(val v: View) : RecyclerView.ViewHolder(v)
}
