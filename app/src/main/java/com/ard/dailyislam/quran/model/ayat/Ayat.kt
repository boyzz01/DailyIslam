package com.ard.dailyislam.quran.model.ayat

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
data class Ayat(
    @PrimaryKey var id : String,
    var ayatList : List<AyatModel>
)




