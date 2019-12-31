package com.cblib.component

import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.cblib.util.CBUtil
import com.cblib.util.font.FontUtil

class CBEditText : AppCompatEditText {

    private var stickKeyboard: Boolean = false

    constructor(context: Context) : super(context, null) {
        init1()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init1()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init1()
    }

    private fun init1() {
        if (!isInEditMode) {
            filters = arrayOf(EMOJI_FILTER)
            typeface = FontUtil.getTypeFace(context, FontUtil.TSN_B5_PLAIN)
        }
    }

    //EditText using dimension sdp
    fun setTextSize(size: Int) {
        //Text Size for EditText using dimension sdp
        super.setTextSize(CBUtil.scaleSize(resources, size))
    }

    fun setMaxLength(maxLength: Int) {
        if (!isInEditMode) {
            //filters = arrayOf(InputFilter.LengthFilter(maxLength), EMOJI_FILTER)
            filters += InputFilter.LengthFilter(maxLength)
        }
    }

    fun setAlphaNumeric(){
        if (!isInEditMode) {
            filters += ALPHA_NUMERIC_FILTER
        }
    }

    fun setAutocapitalize(){
        if (!isInEditMode) {
            filters += InputFilter.AllCaps()
        }
    }

    var EMOJI_FILTER: InputFilter = InputFilter { source, start, end, dest, dstart, dend ->
        for (index in start until end) {

            val type = Character.getType(source[index])
            if (type == Character.SURROGATE.toInt() ||
                type == Character.OTHER_SYMBOL.toInt() ||
                type == Character.ENCLOSING_MARK.toInt() ||
                type == Character.COMBINING_SPACING_MARK.toInt() ||
                containsIllegalCharacters(source.toString())
            ) {
                return@InputFilter ""
            }
        }
        null
    }

    private fun containsIllegalCharacters(displayName: String): Boolean {
        val nameLength = displayName.length

        for (i in 0 until nameLength) {
            val hs = displayName[i]

            if (0xd800 <= hs.toInt() && hs.toInt() <= 0xdbff) {
                val ls = displayName[i + 1]
                val uc = (hs.toInt() - 0xd800) * 0x400 + (ls.toInt() - 0xdc00) + 0x10000

                if (0x1d000 <= uc && uc <= 0x1f77f) {
                    return true
                }
            } else if (Character.isHighSurrogate(hs)) {
                val ls = displayName[i + 1]

                if (ls.toInt() == 0x20e3) {
                    return true
                }
            } else {
                // non surrogate
                if (0x2100 <= hs.toInt() && hs.toInt() <= 0x27ff) {
                    return true
                } else if (0x2B05 <= hs.toInt() && hs.toInt() <= 0x2b07) {
                    return true
                } else if (0x2934 <= hs.toInt() && hs.toInt() <= 0x2935) {
                    return true
                } else if (0x3297 <= hs.toInt() && hs.toInt() <= 0x3299) {
                    return true
                } else if (hs.toInt() == 0xa9 || hs.toInt() == 0xae || hs.toInt() == 0x303d || hs.toInt() == 0x3030 || hs.toInt() == 0x2b55 || hs.toInt() == 0x2b1c || hs.toInt() == 0x2b1b || hs.toInt() == 0x2b50) {
                    return true
                }
            }
        }

        return false
    }

    var ALPHA_NUMERIC_FILTER: InputFilter = InputFilter { source, start, end, dest, dstart, dend ->
        // Only keep characters that are alphanumeric
        val builder = StringBuilder()
        for (i in start until end) {
            val c = source[i]
            if (Character.isLetterOrDigit(c)) {
                builder.append(c)
            }
        }

        // If all characters are valid, return null, otherwise only return the filtered characters
        val allCharactersValid = (builder.length == end - start)
        if (allCharactersValid)
            null
        else
            builder.toString()
    }

    fun isAmount() {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    }

    fun isNumeric() {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    }

    fun isPhone() {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    }

    fun isPassword() {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
    }

    fun isPasswordNumeric() {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
    }

    fun isEmail() {
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
    }

    fun isNormal() {
        inputType = InputType.TYPE_CLASS_TEXT
    }

    fun isNormalMultiLine() {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        setSingleLine(false)
    }



    /*********************************/
    fun setTypeface(_typeface: String) {
        if (!isInEditMode) {
            typeface = FontUtil.getTypeFace(context, _typeface)
        }
    }

    fun setStickKeyboard(stickKeyboard: Boolean) {
        this.stickKeyboard = stickKeyboard
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        return stickKeyboard
    }
}