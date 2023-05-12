package com.example.marsservice.network

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

@Parcelize
data class MarsProperty(
    val id: String?,
    @Json(name = "img_src") val imgSrcUrl: String?,
    val type: String?,
    val price: Double) : Parcelable {
    val isRental
        get() = type == Type.RENT.text
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imgSrcUrl)
        parcel.writeString(type)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarsProperty> {
        override fun createFromParcel(parcel: Parcel): MarsProperty {
            return MarsProperty(parcel)
        }

        override fun newArray(size: Int): Array<MarsProperty?> {
            return arrayOfNulls(size)
        }
    }
}

enum class Type(val text: String){
    RENT("rent")
}

annotation class Parcelize