package com.curso.homebakery

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Receta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "harina") val harina: Double,
    @ColumnInfo(name = "hidratacion")val hidratacion: Double,
    @ColumnInfo(name = "sal") val sal : Double,
    @ColumnInfo(name = "masaMadre")val MasaMadre : Double
)
