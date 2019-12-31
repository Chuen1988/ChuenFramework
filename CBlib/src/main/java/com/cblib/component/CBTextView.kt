package com.cblib.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.cblib.util.CBUtil
import com.cblib.util.font.CustomTypefaceSpan
import com.cblib.util.font.FontUtil
import java.util.*

class CBTextView : AppCompatTextView {

    //TextView Color
    var textViewColor: Int = 0

    private var mHelper: CBAutoFitHelper? = null

    constructor(context: Context) : super(context, null) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @SuppressLint("Recycle")
    fun init(attrs: AttributeSet?) {
        if (!isInEditMode) {
            mHelper = CBAutoFitHelper.create(this, attrs, 0)
            textViewColor = Color.BLACK

            if (attrs != null) {
                val set = intArrayOf(
                    android.R.attr.textColor
                )
                val typedArray = context.obtainStyledAttributes(attrs, set)
                textViewColor = typedArray.getColor(0, textViewColor)
            }

            refresh()
        }
    }

    fun refresh(){
        super.setTypeface(FontUtil.getTypeFace(context, FontUtil.TSN_B5_PLAIN))
        super.setTextColor(textViewColor)
    }

    //Text View using dimension sdp
    fun setTextSize(size: Int) {
        //Text View using dimension sdp
        super.setTextSize(CBUtil.scaleSize(resources, size))
        mHelper?.textSize = CBUtil.scaleSize(resources, size)
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        mHelper?.setTextSize(unit, size)
    }

    override fun setLines(lines: Int) {
        super.setLines(lines)
        mHelper?.maxLines = lines
    }

    override fun setMaxLines(maxLines: Int) {
        super.setMaxLines(maxLines)
        mHelper?.maxLines = maxLines
    }

    /**
     * Returns the AutofitHelper for this View.
     */
    fun getAutofitHelper(): CBAutoFitHelper? {
        return mHelper
    }

    /**
     * Returns whether or not the text will be automatically re-sized to fit its constraints.
     */
    fun isSizeToFit(): Boolean? {
        return mHelper?.isEnabled
    }

    /**
     * Sets the property of this field (sizeToFit), to automatically resize the text to fit its
     * constraints.
     */
    fun setSizeToFit() {
        setSizeToFit(true)
    }

    /**
     * If true, the text will automatically be re-sized to fit its constraints; if false, it will
     * act like a normal TextView.
     *
     * @param sizeToFit
     */
    private fun setSizeToFit(sizeToFit: Boolean) {
        mHelper?.isEnabled = sizeToFit
    }

    /**
     * Returns the maximum size (in pixels) of the text in this View.
     */
    fun getMaxTextSize(): Float? {
        return mHelper?.maxTextSize
    }

    /**
     * Set the maximum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param size The scaled pixel size.
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(size: Float) {
        mHelper?.maxTextSize = size
    }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(unit: Int, size: Float) {
        mHelper?.setMaxTextSize(unit, size)
    }

    /**
     * Returns the minimum size (in pixels) of the text in this View.
     */
    fun getMinTextSize(): Float? {
        return mHelper?.minTextSize
    }

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param minSize The scaled pixel size.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(minSize: Int) {
        mHelper?.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, CBUtil.scaleSize(resources, minSize))
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param minSize The desired size in the given units.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(unit: Int, minSize: Float) {
        mHelper?.setMinTextSize(unit, minSize)
    }

    /**
     * Returns the amount of precision used to calculate the correct text size to fit within its
     * bounds.
     */
    fun getPrecision(): Float? {
        return mHelper?.precision
    }

    /**
     * Set the amount of precision used to calculate the correct text size to fit within its
     * bounds. Lower precision is more precise and takes more time.
     *
     * @param precision The amount of precision.
     */
    fun setPrecision(precision: Float) {
        mHelper?.precision = precision
    }

    fun setTypeface(typeface: String) {
        super.setTypeface(FontUtil.getTypeFace(context, typeface))
    }

    interface OnClickableMultipleTextClick {
        fun onClick(clickableText: String)
    }

    fun setClickableMultipleText(textClickableList: ArrayList<String>, color: Int, underLine: Boolean, listener: OnClickableMultipleTextClick?) {
        val text = text.toString()
        val spannedClickableString = SpannableString(text)

        for (textClickable in textClickableList) {
            if (text.contains(textClickable)) {
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(textView: View) {
                        if (listener != null) {
                            listener.onClick(textClickable)
                        }
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = underLine
                    }
                }


                val start = text.indexOf(textClickable)
                val end = textClickable.length + start

                spannedClickableString.setSpan(clickableSpan, start, end, 0)

                if (color != 0) {
                    spannedClickableString.setSpan(ForegroundColorSpan(color), start, end, 0)
                }
            }
        }
        setText(spannedClickableString)
        movementMethod = LinkMovementMethod.getInstance()
    }

    fun setClickableText(textClickable: String, color: Int, underLine: Boolean, listener: OnClickListener?) {

        val text = text.toString()
        if (!text.contains(textClickable))
            return

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                listener?.onClick(textView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = underLine
            }
        }

        val start = text.indexOf(textClickable)
        val end = textClickable.length + start

        val spannedClickableString = SpannableString(text)

        spannedClickableString.setSpan(clickableSpan, start, end, 0)

        if (color != 0) {
            spannedClickableString.setSpan(ForegroundColorSpan(color), start, end, 0)
        }

        spannedClickableString.setSpan(CustomTypefaceSpan("", FontUtil.getTypeFace(context, FontUtil.TSN_B8_EXTRA_BOLD)), start, end, 0)

        setText(spannedClickableString)
        movementMethod = LinkMovementMethod.getInstance()
    }
}