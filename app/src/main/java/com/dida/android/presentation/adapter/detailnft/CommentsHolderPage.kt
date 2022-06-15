package com.dida.android.presentation.adapter.detailnft

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.android.R
import com.dida.android.domain.model.nav.detailnft.Comments


class CommentsHolderPage internal constructor(
    itemView: View,
    private val context: Context,
    private val n_itemClickListener: CommentsAdapter.OnItemClickEventListener
) : RecyclerView.ViewHolder(itemView) {

    private val user_img: ImageView
    private val comment_txt: TextView

    var data: Comments? = null
    fun onBind(data: Comments) {
        this.data = data
        // image
        Glide.with(context)
            .load(R.drawable.nft_example_image)
            .transform(CenterCrop(), RoundedCorners(200))
            .into(user_img)

        comment_txt.text = data.contents
    }

    init {
        user_img = itemView.findViewById(R.id.user_img)
        comment_txt = itemView.findViewById(R.id.comment_txt)
    }
}
