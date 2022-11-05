package com.example.network.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CustomUIResponse(
    @SerializedName("logo-url")
    @Expose
    val logoUrl: String?,
    @SerializedName("heading-text")
    @Expose
    val headingText: String?,
    @SerializedName("uidata")
    @Expose
    val uidata: List<Uidata?>?
) {
    data class Uidata(
        @SerializedName("uitype")
        @Expose
        val uitype: String?,
        @SerializedName("value")
        @Expose
        val value: String?,
        @SerializedName("key")
        @Expose
        val key: String?,
        @SerializedName("hint")
        @Expose
        val hint: String?
    )
}