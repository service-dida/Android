package com.dida.android.presentation.adapter.detailnft

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dida.android.R
import com.dida.android.domain.model.nav.detailnft.Comments
import com.dida.android.domain.model.nav.detailnft.Community


class CommunityHolderPage internal constructor(
    itemView: View,
    private val context: Context,
    private val n_itemClickListener: CommunityAdapter.OnItemClickEventListener
) : RecyclerView.ViewHolder(itemView) {

    private val user_img: ImageView
    private val user_name: TextView
    private val clip_btn: ConstraintLayout
    private val contents_name: TextView
    private val contents_txt: TextView
    private val nft_img: ImageView
    private val nft_name: TextView
    private val dida_img: ImageView
    private val dida_price: TextView
    private val comment_recycler: RecyclerView

    var data: Community? = null
    fun onBind(data: Community) {
        this.data = data
        // image
        Glide.with(context)
            .load(R.drawable.nft_example_image)
            .transform(CenterCrop(), RoundedCorners(200))
            .into(user_img)
        user_name.text = data.userName

        contents_name.text = data.contentName
        contents_txt.text = data.contentDetail

        Glide.with(context)
            .load(R.drawable.nft_example_image)
            .transform(CenterCrop(), RoundedCorners(50))
            .into(nft_img)
        nft_name.text = data.nftName

        Glide.with(context)
            .load(R.drawable.nft_example_image)
            .transform(CenterCrop(), RoundedCorners(200))
            .into(dida_img)
        dida_price.text = data.didaPrice.toString()+" dida"


        // test
        var commentsAdapter = CommentsAdapter()
        data.Comments.forEach { item ->
            commentsAdapter.addItem(item)
        }
        commentsAdapter.notifyDataSetChanged()

        comment_recycler.apply {
            comment_recycler.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL,
                false)
            adapter = commentsAdapter
            setHasFixedSize(true)
        }

        commentsAdapter.nextItemClickListener(object : CommentsAdapter.OnItemClickEventListener {
            override fun onItemClick(a_view: View?, a_position: Int) {
                n_itemClickListener!!.onItemClick(a_view, a_position)
            }
        })
    }

    init {
        user_img = itemView.findViewById(R.id.user_img)
        user_name = itemView.findViewById(R.id.user_name)
        clip_btn = itemView.findViewById(R.id.clip_btn)
        contents_name = itemView.findViewById(R.id.contents_name)
        contents_txt = itemView.findViewById(R.id.contents_txt)
        nft_img = itemView.findViewById(R.id.nft_img)
        nft_name = itemView.findViewById(R.id.nft_name)
        dida_img = itemView.findViewById(R.id.dida_img)
        dida_price = itemView.findViewById(R.id.dida_price)
        comment_recycler = itemView.findViewById(R.id.comment_recycler)
    }
}
