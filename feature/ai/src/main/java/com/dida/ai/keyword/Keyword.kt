package com.dida.ai.keyword

sealed class Keyword {

    abstract val word: String

    data class Default(
        override val word: String
    ): Keyword()

    data class Color(
        val color: androidx.compose.ui.graphics.Color,
        override val word: String
    ): Keyword()

    data class Style(
        val imageUrl: Int,
        override val word: String
    ): Keyword()

    object Init: Keyword() {
        override val word: String = ""
    }
}

fun String.toKeyword(): Keyword.Default {
    return Keyword.Default(this)
}
