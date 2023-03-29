package com.dida.common.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dida.common.R

class LineIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val backgroundView: View
    private val foregroundView: View
    private val percentGuideline: Guideline

    init {
        val root = View.inflate(context, R.layout.line_indicator_view, this)
        backgroundView = root.findViewById(R.id.indicator_background_view)
        foregroundView = root.findViewById(R.id.indicator_foreground_view)
        percentGuideline = root.findViewById(R.id.indicator_percent_guideline)

        context.obtainStyledAttributes(attrs, R.styleable.LineIndicatorView).use { array ->
            val fillColor = array.getColor(R.styleable.LineIndicatorView_liv_fillColor, ContextCompat.getColor(context, R.color.n1))
            val baseColor = array.getColor(R.styleable.LineIndicatorView_liv_baseColor, ContextCompat.getColor(context, R.color.n6))
            foregroundView.background = ColorDrawable(fillColor)
            backgroundView.background = ColorDrawable(baseColor)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            post { setCurrentCount(recyclerView.computeScrollPercent()) }
        }
    }

    private val infiniteScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            if (!(firstVisibleItem == 0 || firstVisibleItem == recyclerView.adapter!!.itemCount - 1)) {
                post { setCurrentCount(recyclerView.computeInfiniteScrollPercent()) }
            }
        }
    }

    fun attachRecyclerView(recyclerView: RecyclerView) = post {
        setCurrentCount(recyclerView.computeScrollPercent())
        recyclerView.addOnScrollListener(scrollListener)
    }

    fun attachInfiniteRecyclerView(recyclerView: RecyclerView) = post {
        recyclerView.adapter?.let { adapter ->
            if (adapter.itemCount > 1) {
                setCurrentCount(recyclerView.computeInfiniteScrollPercent())
                recyclerView.addOnScrollListener(infiniteScrollListener)
            } else {
                setCurrentCount(recyclerView.computeScrollPercent())
                recyclerView.addOnScrollListener(scrollListener)
            }
        }
    }

    fun attachViewPager2(viewPager2: ViewPager2) = post {
        setCurrentCount(0f)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val count = (viewPager2.adapter?.itemCount ?: 0) - 1
                if (count > 0) {
                    val itemPercent = 1 / count.toFloat()
                    val startPercent = position * itemPercent
                    val currentPercent = startPercent + itemPercent * positionOffset
                    setCurrentCount(currentPercent)
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun setCurrentCount(ratio: Float) = percentGuideline.setGuidelinePercent(ratio)

}

fun RecyclerView.computeScrollPercent(): Float =
    (computeHorizontalScrollExtent() + computeHorizontalScrollOffset()).toFloat() / computeHorizontalScrollRange()

fun RecyclerView.computeInfiniteScrollPercent(): Float =
    computeHorizontalScrollOffset().toFloat() / (computeHorizontalScrollRange() - (2 * computeHorizontalScrollExtent()))

