package com.curso.homebakery

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(tableName = "receta",
    foreignKeys = [ForeignKey(entity = Categoria::class,
        parentColumns = ["categoriaId"],
        childColumns = ["categoriaId"],
        onDelete = ForeignKey.CASCADE)])
data class Receta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "harina") val harina: Double,
    @ColumnInfo(name = "hidratacion")val hidratacion: Double,
    @ColumnInfo(name = "sal") val sal : Double,
    @ColumnInfo(name = "masaMadre")val MasaMadre : Double,
    @ColumnInfo(name = "categoriaId")val categoriaId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeDouble(harina)
        parcel.writeDouble(hidratacion)
        parcel.writeDouble(sal)
        parcel.writeDouble(MasaMadre)
        parcel.writeInt(categoriaId)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Receta> {
        override fun createFromParcel(parcel: Parcel): Receta {
            return Receta(parcel)
        }

        override fun newArray(size: Int): Array<Receta?> {
            return arrayOfNulls(size)
        }
    }
}
