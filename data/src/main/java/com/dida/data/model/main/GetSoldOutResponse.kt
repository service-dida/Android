package com.dida.data.model.main

import com.dida.domain.main.model.SoldOut

data class GetSoldOutResponse(
    val soldOuts: List<SoldOut>
)
