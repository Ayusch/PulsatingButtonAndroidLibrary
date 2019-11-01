package com.ayusch.pulsatingbutton

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.pulsating_button.view.*


/**
 * Created by Ayus'c'h Jain
 * on 2019-10-26
 *
 */

class PulsatingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {


    private var buttonColor: Int = ContextCompat.getColor(context, R.color.colorAccent)
    private var verticalOffset: Int = 40
    private var horizontalOffset: Int = 40
    private var repeatCount: Int = Int.MAX_VALUE
    private var animationDuration: Int = 1000
    val set = AnimatorSet()

    init {
        attrs?.let {
            parseAttributes(context, it)
        }
        init(context)
    }

    private fun init(context: Context?) {

    }

    private fun parseAttributes(context: Context, attributes: AttributeSet) {
        val attrs =
            context.theme.obtainStyledAttributes(attributes, R.styleable.PulsatingButton, 0, 0)
        try {
            this.animationDuration = attrs.getInt(R.styleable.PulsatingButton_pulseDuration, 100)
            this.verticalOffset = attrs.getInt(R.styleable.PulsatingButton_verticalOffset, 4)
            this.horizontalOffset = attrs.getInt(R.styleable.PulsatingButton_horizontalOffset, 4)
            this.repeatCount = attrs.getInt(R.styleable.PulsatingButton_pulseCount, Int.MAX_VALUE)

        } finally {
            attrs.recycle()
        }
    }

    fun startAnimation() {
        if(viewTreeObserver.isAlive){
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    startAnim(measuredHeight, measuredWidth)
                }
            })
        }
    }

    private fun startAnim(originalHeight: Int, originalWidth: Int) {
        val verticalAnimator = ValueAnimator.ofInt(0, verticalOffset).apply {
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            duration = animationDuration.toLong()
        }

        val horizontalAnimator = ValueAnimator.ofInt(0, horizontalOffset).apply {
            repeatMode = ValueAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
            duration = animationDuration.toLong()
        }
        verticalAnimator.addUpdateListener { valueAnimator ->
            val params = layoutParams
            val animatedValue = valueAnimator.animatedValue as Int
            params.height = (originalHeight + animatedValue)
            layoutParams = params
        }

        horizontalAnimator.addUpdateListener { valueAnimator ->
            val params =  layoutParams
            val animatedValue = valueAnimator.animatedValue as Int
            params.width = (originalWidth + animatedValue)
            layoutParams = params
        }

        if (repeatCount == Int.MAX_VALUE) {
            verticalAnimator.repeatCount = Animation.INFINITE
            horizontalAnimator.repeatCount = Animation.INFINITE
        } else {
            verticalAnimator.repeatCount = repeatCount
            horizontalAnimator.repeatCount = repeatCount
        }

        set.playTogether(verticalAnimator, horizontalAnimator)
        set.start()
    }


    fun setHorizontalOffset(horizontalOffset: Int) {
        this.horizontalOffset = horizontalOffset
    }

    fun setVerticalOffset(verticalOffset: Int) {
        this.verticalOffset = verticalOffset
    }

    fun setRepeatCount(count: Int) {
        this.repeatCount = count
    }

    fun setAnimationDuration(duration: Int) {
        this.animationDuration = duration
    }

    fun stopAnimation() {
        set.removeAllListeners()
        set.end()
        set.cancel()
    }

}
