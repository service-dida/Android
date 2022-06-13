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
import com.dida.android.domain.model.nav.home.HotSeller
import com.dida.android.domain.model.nav.home.Hots
import com.dida.android.domain.model.nav.home.SoldOut


class SoldOutHolderPage internal constructor(
    itemView: View,
    private val context: Context,
    private val n_itemClickListener: SoldOutAdapter.OnItemClickEventListener
) : RecyclerView.ViewHolder(itemView) {

    private val nft_img: ImageView
    private val nft_name: TextView
    private val user_img: ImageView
    private val user_name: TextView
    private val price_txt: TextView

    var data: SoldOut? = null
    fun onBind(data: SoldOut) {
        this.data = data

        user_name.text = data.userName
        nft_name.text = data.nftName
        price_txt.text = data.price.toString()
        // image
//        Glide.with(context)
//            .load(data.nftImg)
//            .fitCenter()
//            .into(hots_img)
        // image sample
        user_img.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.nft_example_image))
        nft_img.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.nft_example_image))

//        hotsContentsMain.setOnClickListener { a_view ->
//            val position = absoluteAdapterPosition
//            if (position != RecyclerView.NO_POSITION) {
//                n_itemClickListener!!.onItemClick(a_view, position)
//            }
//        }
    }

    init {
        nft_img = itemView.findViewById(R.id.nft_img)
        nft_name = itemView.findViewById(R.id.nft_name)
        user_img = itemView.findViewById(R.id.user_img)
        user_name = itemView.findViewById(R.id.user_name)
        price_txt = itemView.findViewById(R.id.price_txt)
    }
}
