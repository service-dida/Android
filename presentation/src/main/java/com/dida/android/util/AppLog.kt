package com.dida.android.util

import android.util.Log

/**
 *  AppLog
 *  출력 위치를 리포트함
 */
class AppLog {
    companion object {
        private const val LOG_TAG = "DIDA"

        @JvmStatic
        fun v(message: String?) {
            val str = msgNullChecked(message)
            val stack = Throwable().stackTrace
            val currentStack = stack[1]
            Log.v(LOG_TAG, stackInfoPrint(currentStack, str))
        }

        @JvmStatic
        fun v(tag: String?, message: String?) {
            val str = msgNullChecked(message)
            val stack = Throwable().stackTrace
            val currentStack = stack[1]
            Log.v(tag, stackInfoPrint(currentStack, str))
        }

        @JvmStatic
        fun d(e: Exception) {
            val msg = StringBuilder().apply {
                append(e.message)
                append("\n")
                e.stackTrace.forEachIndexed { index, stackTraceElement ->
                    append(stackTraceElement.toString())
                    append("\n")
                    if (index > 7) return@forEachIndexed
                }
            }.toString()
            d(LOG_TAG, msg)
        }

        @JvmStatic
        fun d(message: String?) {
            val str = msgNullChecked(message)
            val stack = Throwable().stackTrace
            val currentStack = stack[1]
            val MAX_LEN = 3000

            val len: Int = str.length
            if (len > MAX_LEN) {
                var idx = 0
                var nextIdx = 0
                while (idx < len) {
                    nextIdx += MAX_LEN
                    Log.d(
                        LOG_TAG,
                        stackInfoPrint(
                            currentStack,
                            str.substring(idx, if (nextIdx > len) len else nextIdx)
                        )
                    )
                    idx = nextIdx
                }
            } else {
                Log.d(LOG_TAG, stackInfoPrint(currentStack, str))
            }
        }

        @JvmStatic
        fun d(tag: String?, message: String?) {
            val str = msgNullChecked(message)
            val stack = Throwable().stackTrace
            val currentStack = stack[1]
            val MAX_LEN = 3500

            val len: Int = str.length
            if (len > MAX_LEN) {
                var idx = 0
                var nextIdx = 0
                while (idx < len) {
                    nextIdx += MAX_LEN
                    Log.d(
                        tag,
                        stackInfoPrint(
                            currentStack,
                            str.substring(idx, if (nextIdx > len) len else nextIdx)
                        )
                    )
                    idx = nextIdx
                }
            } else {
                Log.d(tag, stackInfoPrint(currentStack, str))
            }
        }

        @JvmStatic
        fun i(message: String?) {
            Log.i(LOG_TAG, message!!)
        }

        @JvmStatic
        fun i(tag: String?, message: String?) {
            Log.i(tag, message!!)
        }

        @JvmStatic
        fun w(message: String?) {
            Log.w(LOG_TAG, message!!)
        }

        @JvmStatic
        fun w(tag: String?, message: String?) {
            Log.w(tag, message!!)
        }

        @JvmStatic
        fun e(message: String?) {
            val str = msgNullChecked(message)
            val stack = Throwable().stackTrace
            val currentStack = stack[1]
            Log.e(LOG_TAG, stackInfoPrint(currentStack, str))
        }

        @JvmStatic
        fun e(tag: String?, message: String?) {
            val str = msgNullChecked(message)
            val stack = Throwable().stackTrace
            val currentStack = stack[1]
            Log.e(tag, stackInfoPrint(currentStack, str))
        }

        @JvmStatic
        fun e(e: Exception) {
            val msg = StringBuilder().apply {
                append(e.message)
                append("\n")
                e.stackTrace.forEachIndexed { index, stackTraceElement ->
                    append(stackTraceElement.toString())
                    append("\n")
                    if (index > 7) return@forEachIndexed
                }
            }.toString()
            e(LOG_TAG, msg)
        }

        @JvmStatic
        fun msgNullChecked(msg: String?): String {
            return msg ?: "null"
        }

        private fun stackInfoPrint(currentStack: StackTraceElement, str: String) =
            currentStack.className + "::" +
                    currentStack.methodName + "(" +
                    currentStack.lineNumber + ")" +
                    ": " + str
    }
}