package com.dida.common.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dida.common.util.SLIDETYPE
import kotlin.math.max

class CirclePagerIndicatorDecoration(
    private val slideType: SLIDETYPE = SLIDETYPE.NORMAL,
    private val activeColor: Int,
    private val inactiveColor: Int,
    private val indicatorHeight: Int,
    private val indicatorStrokeWidth: Float = (indicatorHeight / 2).toFloat(),
    private val indicatorStrokeColor: Int = 0,
    private val indicatorItemLength: Int = indicatorHeight / 2,
    private val indicatorItemPadding: Int = (indicatorHeight * 1.5).toInt(),
    private val indicatorMarginTop: Float = 0f,
) : RecyclerView.ItemDecoration() {

    /**
     * Some more natural animation interpolation
     */
    private val interpolator: Interpolator = AccelerateDecelerateInterpolator()
    private val paint = Paint()
    private val strokePaint = Paint()
    private val transParentPaint = Paint()

    init {
        paint.apply {
            style = Paint.Style.STROKE
            strokeWidth = (indicatorHeight / 2).toFloat()
            isAntiAlias = true
        }

        strokePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = indicatorStrokeWidth
            isAntiAlias = true
            color = indicatorStrokeColor
        }

        transParentPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = (indicatorHeight / 2).toFloat()
            isAntiAlias = true
            color = Color.TRANSPARENT
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = parent.adapter?.itemCount ?: 0

        // center horizontally, calculate width and subtract half from center
        val totalLength = indicatorItemLength * itemCount
        val paddingBetweenItems = max(0, itemCount - 1) * indicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        // center vertically in the allotted space
        val indicatorPosY = (parent.height - indicatorHeight / 2f) + indicatorMarginTop

        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as? LinearLayoutManager

        val activePosition = when (slideType) {
            SLIDETYPE.CAROUSEL, SLIDETYPE.INFINITE -> {
                val firstItem = (layoutManager?.findFirstVisibleItemPosition()) ?: RecyclerView.NO_POSITION
                val lastItem = (layoutManager?.findLastVisibleItemPosition()) ?: RecyclerView.NO_POSITION
                (firstItem + lastItem)/2
            }
            else -> 0
        }

        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager?.findViewByPosition(activePosition)
        val left = activeChild?.left ?: 0
        val width = activeChild?.width ?: 0

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        val progress = interpolator.getInterpolation(left * -1 / width.toFloat())

        when (slideType) {
            SLIDETYPE.CAROUSEL -> {
                drawCarouselInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)
                drawCarouselHighlights(c, indicatorStartX, indicatorPosY, activePosition, itemCount)
            }
            SLIDETYPE.INFINITE -> {
                drawInfiniteInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)
                drawInfiniteHighlights(c, indicatorStartX, indicatorPosY, activePosition)
            }
            else -> {
                drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)
                drawHighlights(c, indicatorStartX, indicatorPosY, activePosition)
            }
        }
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        paint.color = inactiveColor

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorPosY, indicatorItemLength / 2f, paint)
            if (indicatorStrokeColor > 0) c.drawCircle(start, indicatorPosY, indicatorItemLength.toFloat(), strokePaint)
            start += itemWidth
        }
    }

    private fun drawInfiniteInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        paint.color = inactiveColor

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding
        var start = indicatorStartX

        for (i in 0 until itemCount) {
            if (i == 0 || i == (itemCount - 1)) c.drawCircle(start, indicatorPosY, indicatorItemLength / 2f, transParentPaint)
            else c.drawCircle(start, indicatorPosY, indicatorItemLength / 2f, paint)
            if (indicatorStrokeColor > 0) c.drawCircle(start, indicatorPosY, indicatorItemLength.toFloat(), strokePaint)
            start += itemWidth
        }
    }

    private fun drawCarouselInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        paint.color = inactiveColor

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding
        var start = indicatorStartX

        for (i in 0 until itemCount) {
            if (i == 0 || i == 1 || i == (itemCount - 1) || i == (itemCount - 2)) c.drawCircle(start, indicatorPosY, indicatorItemLength / 2f, transParentPaint)
            else c.drawCircle(start, indicatorPosY, indicatorItemLength / 2f, paint)
            if (indicatorStrokeColor > 0) c.drawCircle(start, indicatorPosY, indicatorItemLength.toFloat(), strokePaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        highlightPosition: Int
    ) {
        paint.color = activeColor

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding
        val highlightStart = indicatorStartX + itemWidth * highlightPosition
        c.drawCircle(highlightStart, indicatorPosY, indicatorItemLength / 2f, paint)
        if (indicatorStrokeColor > 0) c.drawCircle(highlightStart, indicatorPosY, indicatorItemLength.toFloat(), strokePaint)
    }

    private fun drawInfiniteHighlights(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        highlightPosition: Int
    ) {
        paint.color = activeColor

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding
        val highlightStart = indicatorStartX + itemWidth * highlightPosition
        if (highlightPosition != 0) c.drawCircle(highlightStart, indicatorPosY, indicatorItemLength / 2f, paint)
        if (indicatorStrokeColor > 0) c.drawCircle(highlightStart, indicatorPosY, indicatorItemLength.toFloat(), strokePaint)
    }

    private fun drawCarouselHighlights(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        highlightPosition: Int,
        itemCount: Int
    ) {
        paint.color = activeColor

        // width of item indicator including padding
        val itemWidth = indicatorItemLength + indicatorItemPadding
        val highlightStart = indicatorStartX + itemWidth * highlightPosition
        if (!(highlightPosition == 0 || highlightPosition == 1 || highlightPosition == (itemCount - 1) || highlightPosition == (itemCount - 2))) c.drawCircle(highlightStart, indicatorPosY, indicatorItemLength / 2f, paint)
        if (indicatorStrokeColor > 0) c.drawCircle(highlightStart, indicatorPosY, indicatorItemLength.toFloat(), strokePaint)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorHeight
    }
}
