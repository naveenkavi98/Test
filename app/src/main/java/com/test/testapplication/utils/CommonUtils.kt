package com.test.testapplication.utils

import android.text.TextUtils
import java.util.*

class CommonUtils {

    companion object {
        fun toCamelCaseSentence(s: String?): String? {
            return if (s != null) {
                val words = s.split(" ".toRegex()).toTypedArray()
                val sb = StringBuilder()
                for (i in words.indices) {
                    sb.append(camelCase(words[i]))
                }
                sb.toString().trim { it <= ' ' }
            } else {
                ""
            }
        }

        fun camelCase(stringToConvert: String): String? {
            return if (TextUtils.isEmpty(stringToConvert)) {
                ""
            } else stringToConvert[0].uppercaseChar().toString() +
                    stringToConvert.substring(1).lowercase(Locale.getDefault()) + " "
        }
    }
}