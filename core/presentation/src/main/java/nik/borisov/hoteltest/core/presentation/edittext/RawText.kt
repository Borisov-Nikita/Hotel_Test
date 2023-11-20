package nik.borisov.hoteltest.core.presentation.edittext

/**
 * Raw text, another words TextWithout mask characters
 */
class RawText {
    var text = ""
        private set

    /**
     * text = 012345678, range = 123 =&gt; text = 0456789
     * @param range given range
     */
    fun subtractFromString(range: Range) {
        var firstPart = ""
        var lastPart = ""
        if (range.start > 0 && range.start <= text.length) {
            firstPart = text.substring(0, range.start)
        }
        if (range.end >= 0 && range.end < text.length) {
            lastPart = text.substring(range.end, text.length)
        }
        text = firstPart + lastPart
    }

    /**
     *
     * @param string New String to be added
     * @param start Position to insert newString
     * @param maxLength Maximum raw text length
     * @return Number of added characters
     */
    fun addToString(newString: String?, start: Int, maxLength: Int): Int {
        var string = newString
        var firstPart = ""
        var lastPart = ""
        if (string == null || string == "") {
            return 0
        } else require(start >= 0) { "Start position must be non-negative" }
        require(start <= text.length) { "Start position must be less than the actual text length" }
        var count = string.length
        if (start > 0) {
            firstPart = text.substring(0, start)
        }
        if (start >= 0 && start < text.length) {
            lastPart = text.substring(start, text.length)
        }
        if (text.length + string.length > maxLength) {
            count = maxLength - text.length
            string = string.substring(0, count)
        }
        text = firstPart + string + lastPart
        return count
    }

    fun length(): Int {
        return text.length
    }

    fun charAt(position: Int): Char {
        return text[position]
    }
}
