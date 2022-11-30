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
    override fun hashCode(): Int {
        return candidates.contentHashCode()
    }

}
data class Place(
    val photos: Array<PlacePhoto>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Place

        if (!photos.contentEquals(other.photos)) return false

        return true
    }

    override fun hashCode(): Int {
        return photos.contentHashCode()
    }
}
data class PlacePhoto(
    @SerializedName("photo_reference")
    val photoReference: String
)
