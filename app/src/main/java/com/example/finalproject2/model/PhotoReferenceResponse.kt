package com.example.finalproject2.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class PhotoReferenceResponse(
    val candidates: Array<Place>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhotoReferenceResponse

        if (!candidates.contentEquals(other.candidates)) return false

        return true
    }

}
