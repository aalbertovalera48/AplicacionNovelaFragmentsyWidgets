package com.example.aplicacionnovela.ui.theme

import android.os.Parcel
import android.os.Parcelable

// Clase Novela que implementa Parcelable para pasar objetos entre actividades
data class Novela(
    var id: String? = null,
    var titulo: String,
    var autor: String,
    var añoPublicacion: Int,
    var sinopsis: String,
    var favorito: Boolean = false
) : Parcelable {

    // Constructor con parcel que sirve para pasar objetos entre actividades
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    // Métodos describeContents e writeToParcel implementados de Parcelable
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

    // Crear un objeto Parcelable de Novela
    companion object CREATOR : Parcelable.Creator<Novela> {
        override fun createFromParcel(parcel: Parcel): Novela {
            return Novela(parcel)
        }

        override fun newArray(size: Int): Array<Novela?> {
            return arrayOfNulls(size)
        }
    }
}
