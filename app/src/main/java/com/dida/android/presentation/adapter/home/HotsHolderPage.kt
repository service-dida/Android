package com.dida.android.presentation.adapter.home

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.android.R
import com.dida.android.domain.model.nav.home.Hots


class HotsHolderPage internal constructor(
    itemView: View,
    private val context: Context,
    private val n_itemClickListener: HotsAdapter.OnItemClickEventListener
) : RecyclerView.ViewHolder(itemView) {

    private val hotsContentsMain: ConstraintLayout
    private val hotsImg: ImageView
    private val hots_name: TextView
    private val hots_coin_txt: TextView

    private val heart_main: ConstraintLayout
    private val heart_count: TextView

    var data: Hots? = null
    fun onBind(data: Hots) {
        this.data = data

        hots_name.text = data.nftName
        hots_coin_txt.text = data.price.toString()+" dida"

        // image
//        Glide.with(context)
//            .load(data.nftImg)
//            .fitCenter()
//            .into(hots_img)
        // image sample
        hotsImg.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.nft_example_image))

        if(data.heartCount == 0.0){
            heart_main.visibility = View.GONE
        }
        else{
            heart_main.visibility = View.VISIBLE
            heart_count.text = data.heartCount.toString()+"K"
        }

//        hotsContentsMain.setOnClickListener { a_view ->
//            val position = absoluteAdapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                n_itemClickListener!!.onItemClick(a_view, position)
//            }
//        }
    }

    init {
        hotsContentsMain = itemView.findViewById(R.id.hots_contents_main)
        hotsImg = itemView.findViewById(R.id.hots_img)
        hots_name = itemView.findViewById(R.id.hots_name)
        hots_coin_txt = itemView.findViewById(R.id.hots_coin_txt)

        heart_main = itemView.findViewById(R.id.heart_main)
        heart_count = itemView.findViewById(R.id.heart_count)
    }
}
