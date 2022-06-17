package com.dida.android.presentation.adapter.home

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.android.R
import com.dida.android.domain.model.nav.home.HotSeller


class HotSellerHolderPage internal constructor(
    itemView: View,
    private val context: Context,
    private val n_itemClickListener: HotSellerAdapter.OnItemClickEventListener
) : RecyclerView.ViewHolder(itemView) {

    private val seller_img: ImageView
    private val user_name: TextView
    private val user_img: ImageView

    var data: HotSeller? = null
    fun onBind(data: HotSeller) {
        this.data = data

        user_name.text = data.sellerName
        // image
//        Glide.with(context)
//            .load(data.nftImg)
//            .fitCenter()
//            .into(hots_img)
        // image sample
        Glide.with(context)
            .load(R.drawable.nft_example_image)
            .transform(CenterCrop(), RoundedCorners(200))
            .into(user_img)

        // rounded
        Glide.with(context)
            .load(R.drawable.nft_example_image)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(seller_img)

//        hotsContentsMain.setOnClickListener { a_view ->
//            val position = absoluteAdapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                n_itemClickListener!!.onItemClick(a_view, position)
//            }
//        }
    }

    init {
        seller_img = itemView.findViewById(R.id.seller_img)
        user_name = itemView.findViewById(R.id.user_name)
        user_img = itemView.findViewById(R.id.user_img)
    }
}
