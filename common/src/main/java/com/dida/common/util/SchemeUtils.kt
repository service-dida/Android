package com.dida.common.util

import android.net.Uri

object SchemeUtils {
    private const val PATH_HOME = "home"
    private const val PATH_DEFI = "defi"
    private const val PATH_ADD = "add"
    private const val PATH_COMMUNITY = "community"
    private const val PATH_MY = "my"

    private const val PARAMS_DETAIL_NFT = "detailNft"
    private const val PARAMS_DETAIL_COMMUNITY = "detailCommunity"

    fun getAppScheme(rawScheme: String): Scheme? {
        return parse(rawScheme)
    }

    private fun parse(rawScheme: String): Scheme? {
        val uri = Uri.parse(rawScheme)
        val remainSegments = uri.pathSegments.toMutableList()
        if (remainSegments.isEmpty()) {
            return null
        }

        val segment = remainSegments.removeAt(0)
        val queryMap = uri.queryToMap()

        return parseFirst(
            uri,
            segment,
            remainSegments,
            queryMap,
            uri.encodedQuery
        )
    }

    private fun parseFirst(uri: Uri, segment: String, remainSegments: MutableList<String>, queryMap: Map<String, String?>, rawQuery: String?): Scheme? {
        return when (segment) {
            PATH_HOME -> Scheme.Home
            PATH_DEFI -> Scheme.Defi
            PATH_ADD -> Scheme.Add
            PATH_COMMUNITY -> Scheme.Community
            PATH_MY -> Scheme.My
            else -> null
        }
    }

    private fun Uri.queryToMap(): Map<String, String?> {
        val map = mutableMapOf<String, String?>()
        queryParameterNames.forEach {
            map[it] = getQueryParameter(it)
        }
        return map
    }
}
