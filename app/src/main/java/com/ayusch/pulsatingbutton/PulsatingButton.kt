package com.ayusch.pulsatingbutton

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.pulsating_button.view.*


/**
 * Created by Ayus'c'h Jain
 * on 2019-10-26
 *
 */

class PulsatingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var buttonText: CharSequence? = ""
    private var textColor: Int = ContextCompat.getColor(context, android.R.color.black)
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
        LayoutInflater.from(context).inflate(R.layout.pulsating_button, this, true)
        setColors()
        setText(buttonText)
    }

    private fun parseAttributes(context: Context, attributes: AttributeSet) {
        val attrs =
            context.theme.obtainStyledAttributes(attributes, R.styleable.PulsatingButton, 0, 0)
        try {
            this.animationDuration = attrs.getInt(R.styleable.PulsatingButton_pulseDuration, 100)
            this.verticalOffset = attrs.getInt(R.styleable.PulsatingButton_verticalOffset, 4)
            this.horizontalOffset = attrs.getInt(R.styleable.PulsatingButton_horizontalOffset, 4)
            this.repeatCount = attrs.getInt(R.styleable.PulsatingButton_pulseCount, Int.MAX_VALUE)
            this.buttonColor = attrs.getColor(
                R.styleable.PulsatingButton_buttonColor,
                ContextCompat.getColor(context, R.color.colorAccent)
            )
            this.textColor = attrs.getColor(
                R.styleable.PulsatingButton_textColor,
                ContextCompat.getColor(context, R.color.colorAccent)
            )
            this.buttonText = attrs.getText(R.styleable.PulsatingButton_text)
        } finally {
            attrs.recycle()
        }
    }

    fun startAnimation() {
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

        val originalHeight = button.layoutParams.height
        val originalWidth = button.layoutParams.width

        verticalAnimator.addUpdateListener { valueAnimator ->
            val params = button.layoutParams
            val animatedValue = valueAnimator.animatedValue as Int
            params.height = (originalHeight + animatedValue)
            button.layoutParams = params
        }

        horizontalAnimator.addUpdateListener { valueAnimator ->
            val params = button.layoutParams
            val animatedValue = valueAnimator.animatedValue as Int
            params.width = (originalWidth + animatedValue)
            button.layoutParams = params
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

    private fun setColors() {
        button.setBackgroundColor(buttonColor)
        button.setTextColor(textColor)
    }

    fun setButtonDrawable(drawable: Drawable) {
        button.background = drawable
    }

    fun setText(text: CharSequence?) {
        if (!TextUtils.isEmpty(text)) {
            button.text = text
        }
    }

    fun stopAnimation() {
        set.removeAllListeners()
        set.end()
        set.cancel()
    }

}