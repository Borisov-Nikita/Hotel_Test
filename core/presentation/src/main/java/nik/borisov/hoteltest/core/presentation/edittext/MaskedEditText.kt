package nik.borisov.hoteltest.core.presentation.edittext

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView.OnEditorActionListener
import com.google.android.material.textfield.TextInputEditText
import nik.borisov.hoteltest.core.presentation.R
import kotlin.properties.Delegates

class MaskedEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : TextInputEditText(context, attrs, defStyleAttr), TextWatcher {

    private val onEditorActionListener: OnEditorActionListener =
        OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                else -> true
            }
        }

    private var mask: String? = null

    private var maskHint: String? = null

    private var charRepresentation = 0.toChar()
    private var keepHint = false
    private var rawToMask by Delegates.notNull<IntArray>()
    private var rawText: RawText? = null
    private var editingBefore = false
    private var editingOnChanged = false
    private var editingAfter = false
    private var maskToRaw by Delegates.notNull<IntArray>()
    private var selection = 0
    private var initialized = false
    private var ignore = false
    protected var maxRawLength = 0
    private var lastValidMaskPosition = 0
    private var selectionChanged = false
    private var focusChangeListener: OnFocusChangeListener? = null
    private var allowedChars: String? = null
    private var deniedChars: String? = null

    private var needShowText = false

    var isKeepingText = false
        private set

    init {
        addTextChangedListener(this)

        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText)
            mask = attributes.getString(R.styleable.MaskedEditText_mask)

            maskHint = attributes.getString(R.styleable.MaskedEditText_mask_hint)

            allowedChars = attributes.getString(R.styleable.MaskedEditText_allowed_chars)
            deniedChars = attributes.getString(R.styleable.MaskedEditText_denied_chars)
            val enableImeAction =
                attributes.getBoolean(R.styleable.MaskedEditText_enable_ime_action, false)
            val representation =
                attributes.getString(R.styleable.MaskedEditText_char_representation)
            charRepresentation = if (representation == null) {
                '#'
            } else {
                representation[0]
            }
            keepHint = attributes.getBoolean(R.styleable.MaskedEditText_keep_hint, false)

            cleanUp()

            // Ignoring enter key presses if needed
            if (!enableImeAction) {
                setOnEditorActionListener(onEditorActionListener)
            } else {
                setOnEditorActionListener(null)
            }
            attributes.recycle()
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superParcelable: Parcelable? = super.onSaveInstanceState()
        val state = Bundle()
        state.putParcelable("super", superParcelable)

        //TODO maskHint

        state.putString("text", getRawText())
        state.putBoolean("keepHint", isKeepHint())
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val bundle = state as Bundle
        keepHint = bundle.getBoolean("keepHint", false)
        super.onRestoreInstanceState(state.getParcelable("super"))
        val text = bundle.getString("text")
        setText(text)
        Log.d(ContentValues.TAG, "onRestoreInstanceState: $text")
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
//		if (text == null || text.equals("")) return;
        super.setText(text, type)
    }

    /** @param listener - its onFocusChange() method will be called before performing MaskedEditText operations,
     * related to this event.
     */
    override fun setOnFocusChangeListener(listener: OnFocusChangeListener?) {
        focusChangeListener = listener
    }

    private fun cleanUp() {
        initialized = false
        if (mask == null || mask!!.isEmpty()) {
            return
        }
        generatePositionArrays()
        if (!isKeepingText || rawText == null) {
            rawText = RawText()
            selection = rawToMask[0]
        }
        editingBefore = true
        editingOnChanged = true
        editingAfter = true
        editingBefore = false
        editingOnChanged = false
        editingAfter = false
        maxRawLength = maskToRaw[previousValidPosition(mask!!.length - 1)] + 1
        lastValidMaskPosition = findLastValidMaskPosition()
        initialized = true
        super.setOnFocusChangeListener { v, hasFocus ->
            if (focusChangeListener != null) {
                focusChangeListener?.onFocusChange(v, hasFocus)
            }
            if (hasFocus()) {
                selectionChanged = false
                if (!needShowText) {
                    if (hasHint() && rawText?.length() == 0) {
                        setText(makeMaskedTextWithHint())
                    } else {
                        setText(makeMaskedText())
                    }
                }
                this@MaskedEditText.setSelection(lastValidPosition())
            }
        }
    }

    private fun findLastValidMaskPosition(): Int {
        for (i in maskToRaw.indices.reversed()) {
            if (maskToRaw[i] != -1) return i
        }
        throw RuntimeException("Mask must contain at least one representation char")
    }

    private fun hasHint(): Boolean {
        return maskHint != null
    }

    fun setShouldKeepText(shouldKeepText: Boolean) {
        isKeepingText = shouldKeepText
    }

    fun setMask(mask: String?) {
        this.mask = mask
        cleanUp()
    }

    fun getMask(): String? {
        return mask
    }

    fun setImeActionEnabled(isEnabled: Boolean) {
        if (isEnabled) setOnEditorActionListener(onEditorActionListener) else setOnEditorActionListener(
            null
        )
    }

    fun getRawText(): String {
        return rawText?.text ?: ""
    }

    fun setCharRepresentation(charRepresentation: Char) {
        this.charRepresentation = charRepresentation
        cleanUp()
    }

    fun getCharRepresentation(): Char {
        return charRepresentation
    }

    /**
     * Generates positions for values characters. For instance:
     * Input data: mask = "+7(###)###-##-##
     * After method execution:
     * rawToMask = [3, 4, 5, 6, 8, 9, 11, 12, 14, 15]
     * maskToRaw = [-1, -1, -1, 0, 1, 2, -1, 3, 4, 5, -1, 6, 7, -1, 8, 9]
     * charsInMask = "+7()- " (and space, yes)
     */
    private fun generatePositionArrays() {
        val aux = IntArray(mask!!.length)
        maskToRaw = IntArray(mask!!.length)
        var charsInMaskAux = ""
        var charIndex = 0
        for (i in 0 until mask!!.length) {
            val currentChar = mask!![i]
            if (currentChar == charRepresentation) {
                aux[charIndex] = i
                maskToRaw[i] = charIndex++
            } else {
                val charAsString = currentChar.toString()
                if (!charsInMaskAux.contains(charAsString)) {
                    charsInMaskAux += charAsString
                }
                maskToRaw[i] = -1
            }
        }
        if (charsInMaskAux.indexOf(' ') < 0) {
            charsInMaskAux += SPACE
        }
        val charsInMask = charsInMaskAux.toCharArray()
        rawToMask = IntArray(charIndex)
        System.arraycopy(aux, 0, rawToMask, 0, charIndex)
    }

    override fun beforeTextChanged(
        s: CharSequence, start: Int, count: Int,
        after: Int
    ) {
        if (!needShowText) return
        if (!editingBefore) {
            editingBefore = true
            if (start > lastValidMaskPosition) {
                ignore = true
            }
            var rangeStart = start
            if (after == 0) {
                rangeStart = erasingStart(start)
            }
            val range: Range = calculateRange(rangeStart, start + count)
            if (range.start != -1) {
                rawText?.subtractFromString(range)
            }
            if (count > 0) {
                selection = previousValidPosition(start)
            }
        }
    }

    private fun erasingStart(startValue: Int): Int {
        var start = startValue
        while (start > 0 && maskToRaw[start] == -1) {
            start--
        }
        return start
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, countValue: Int) {
        if (!needShowText) return
        var count = countValue
        if (!editingOnChanged && editingBefore) {
            editingOnChanged = true
            if (ignore) {
                return
            }
            if (count > 0) {
                val startingPosition = maskToRaw[nextValidPosition(start)]
                val addedString = s.subSequence(start, start + count).toString()
                count = rawText!!.addToString(clear(addedString), startingPosition, maxRawLength)
                if (initialized) {
                    val currentPosition: Int =
                        if (startingPosition + count < rawToMask.size) rawToMask[startingPosition + count] else lastValidMaskPosition + 1
                    selection = nextValidPosition(currentPosition)
                }
            }
        }
    }

    override fun afterTextChanged(s: Editable) {
        if (!needShowText) {
            needShowText = true
            return
        }
        if (!editingAfter && editingBefore && editingOnChanged) {
            editingAfter = true
            if (hasHint() && (keepHint || rawText?.length() == 0)) {
                setText(makeMaskedTextWithHint())
            } else {
                setText(makeMaskedText())
            }
            selectionChanged = false
            setSelection(selection)
            editingBefore = false
            editingOnChanged = false
            editingAfter = false
            ignore = false
        }
    }

    fun isKeepHint(): Boolean {
        return keepHint
    }

    fun setKeepHint(keepHint: Boolean) {
        this.keepHint = keepHint
        setText(getRawText())
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        var sStart = selStart
        var sEnd = selEnd
        if (initialized) {
            if (!selectionChanged) {
                sStart = fixSelection(sStart)
                sEnd = fixSelection(sEnd)

                if (sStart > (text?.length ?: 0)) sStart = text?.length ?: 0
                if (sStart < 0) sStart = 0

                if (sEnd > (text?.length ?: 0)) sEnd = text?.length ?: 0
                if (sEnd < 0) sEnd = 0
                setSelection(sStart, sEnd)
                selectionChanged = true
            } else {
                if (sStart > rawText!!.length() - 1) {
                    val start = fixSelection(sStart)
                    val end = fixSelection(sEnd)
                    if (start >= 0 && end < (text?.length ?: 0)) {
                        setSelection(start, end)
                    }
                }
            }
        }
        super.onSelectionChanged(sStart, sEnd)
    }

    private fun fixSelection(selection: Int): Int {
        return if (selection > lastValidPosition()) {
            lastValidPosition()
        } else {
            nextValidPosition(selection)
        }
    }

    private fun nextValidPosition(currentPosition: Int): Int {
        var position = currentPosition
        while (position < lastValidMaskPosition && maskToRaw[position] == -1) {
            position++
        }
        return if (position > lastValidMaskPosition) lastValidMaskPosition + 1 else position
    }

    private fun previousValidPosition(currentPosition: Int): Int {
        var position = currentPosition
        while (position >= 0 && maskToRaw[position] == -1) {
            position--
            if (position < 0) {
                return nextValidPosition(0)
            }
        }
        return position
    }

    private fun lastValidPosition(): Int {
        return if (rawText?.length() == maxRawLength) {
            rawToMask[rawText!!.length() - 1] + 1
        } else nextValidPosition(rawToMask[rawText?.length() ?: 0])
    }

    private fun makeMaskedText(): String {
        val maskedTextLength: Int = if ((rawText?.length() ?: 0) < rawToMask.size) {
            rawToMask[rawText?.length() ?: 0]
        } else {
            mask!!.length
        }
        val maskedText =
            CharArray(maskedTextLength) //mask.replace(charRepresentation, ' ').toCharArray();
        for (i in maskedText.indices) {
            val rawIndex = maskToRaw[i]
            if (rawIndex == -1) {
                maskedText[i] = mask!![i]
            } else {
                maskedText[i] = rawText!!.charAt(rawIndex)
            }
        }
        return maskedText.toString()
    }

    private fun makeMaskedTextWithHint(): CharSequence {
        val ssb = SpannableStringBuilder()
        var mtrv: Int
        val maskFirstChunkEnd = rawToMask[0]
        for (i in 0 until mask!!.length) {
            mtrv = maskToRaw[i]
            if (mtrv != -1) {
                if (mtrv < rawText!!.length()) {
                    ssb.append(rawText!!.charAt(mtrv))
                } else {
                    ssb.append(maskHint!![maskToRaw[i]])
                }
            } else {
                ssb.append(mask!![i])
            }
            if (keepHint && rawText!!.length() < rawToMask.size && i >= rawToMask[rawText!!.length()] || !keepHint && i >= maskFirstChunkEnd) {
                ssb.setSpan(ForegroundColorSpan(currentHintTextColor), i, i + 1, 0)
            }
        }
        return ssb
    }

    private fun calculateRange(start: Int, end: Int): Range {
        val range = Range()
        var i = start
        while (i <= end && i < mask!!.length) {
            if (maskToRaw[i] != -1) {
                if (range.start == -1) {
                    range.start = maskToRaw[i]
                }
                range.end = maskToRaw[i]
            }
            i++
        }
        if (end == mask!!.length) {
            range.end = rawText!!.length()
        }
        if (range.start == range.end && start < end) {
            val newStart = previousValidPosition(range.start - 1)
            if (newStart < range.start) {
                range.start = newStart
            }
        }
        return range
    }

    private fun clear(string: String): String {
        var newString = string
        if (deniedChars != null) {
            for (c in deniedChars!!.toCharArray()) {
                newString = newString.replace(Character.toString(c), "")
            }
        }
        if (allowedChars != null) {
            val builder = StringBuilder(newString.length)
            for (c in newString.toCharArray()) {
                if (allowedChars!!.contains(c.toString())) {
                    builder.append(c)
                }
            }
            newString = builder.toString()
        }
        return newString
    }

    companion object {
        const val SPACE = " "
    }
}