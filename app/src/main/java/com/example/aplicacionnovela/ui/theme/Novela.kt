package com.example.aplicacionnovela.ui.theme

import android.os.Parcel
import android.os.Parcelable


data class Novela(
    var id: String? = null,
    var titulo: String = "",
    var autor: String = "",
    var añoPublicacion: Int = 0,
    var sinopsis: String = "",
    var favorito: Boolean = false,
    var reseñas: List<String> = emptyList()
) : Parcelable {
    constructor() : this(null, "", "", 0, "", false)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(titulo)
        parcel.writeString(autor)
        parcel.writeInt(añoPublicacion)
        parcel.writeString(sinopsis)
        parcel.writeByte(if (favorito) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<Novela> {
        override fun createFromParcel(parcel: Parcel): Novela {
            return Novela(parcel)
        }

        override fun newArray(size: Int): Array<Novela?> {
            return arrayOfNulls(size)
        }
    }
}
