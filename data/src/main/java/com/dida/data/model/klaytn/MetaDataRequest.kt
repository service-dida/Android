package com.dida.data.model.klaytn

import com.google.gson.annotations.SerializedName

class MetaDataRequest(val metadata: MetaData)

class MetaData(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String)

