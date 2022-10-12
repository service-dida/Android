package com.dida.android.util

import android.view.View
import androidx.annotation.IntDef
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addSnapPagerScroll() {
    // PagerSnapHelper 추가
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(this)
}

class SnapPagerScrollListener(
    snapHelper: PagerSnapHelper,
    @Type type: Int,
    notifyOnInit: Boolean,
    listener: OnChangeListener
) :
    RecyclerView.OnScrollListener() {
    @IntDef(ON_SCROLL, ON_SETTLED)
    annotation class Type
    interface OnChangeListener {
        fun onSnapped(position: Int)
    }

    // Properties
    private val snapHelper: PagerSnapHelper
    private val type: Int
    private val notifyOnInit: Boolean
    private val listener: OnChangeListener
    private var snapPosition: Int

    // Methods
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (type == ON_SCROLL || !hasItemPosition()) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (type == ON_SETTLED && newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    private fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager: RecyclerView.LayoutManager = recyclerView.getLayoutManager()
            ?: return RecyclerView.NO_POSITION
        val snapView: View =
            snapHelper.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private fun notifyListenerIfNeeded(newSnapPosition: Int) {
        if (snapPosition != newSnapPosition) {
            if (notifyOnInit && !hasItemPosition()) {
                listener.onSnapped(newSnapPosition)
            } else if (hasItemPosition()) {
                listener.onSnapped(newSnapPosition)
            }
            snapPosition = newSnapPosition
        }
    }

    private fun hasItemPosition(): Boolean {
        return snapPosition != RecyclerView.NO_POSITION
    }

    companion object {
        // Constants
        const val ON_SCROLL = 0
        const val ON_SETTLED = 1
    }

    // Constructor
    init {
        this.snapHelper = snapHelper
        this.type = type
        this.notifyOnInit = notifyOnInit
        this.listener = listener
        snapPosition = RecyclerView.NO_POSITION
    }
}

