package com.ard.dailyislam.quran.model.surah

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Surah (
    @PrimaryKey val nomor : Int,
    val nama : String,
    val asma : String,
    val name : String,
    val start : Int,
    val ayat : Int,
    val type : String,
    val urut : Int,
    val rukuk : Int,
    val arti : String,
    val keterangan : String
) : Parcelable {
    data class SurahResponse(
        var surahs: MutableList<Surah>
    )
}