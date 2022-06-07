package com.dida.android.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpacing(
    val mSpacing : Int,
    val mTopSpacing : Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val index = view.layoutParams as GridLayoutManager.LayoutParams
        //index.spanIndex
        val position = parent.getChildLayoutPosition(view)
        if(index.spanIndex ==0){
            outRect.right = mSpacing/2
        }else{
            outRect.left = mSpacing/2
        }

        if(position<2){
            outRect.top = 0
        }else{
            outRect.top = mTopSpacing
        }
    }
}