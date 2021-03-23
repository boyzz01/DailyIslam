package com.ard.quran.model.surah;

import com.ard.quran.model.surah.Surah;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurahModel {

    @SerializedName("hasil")
    @Expose

    public List<Surah> surah = null;

    public List<Surah> setSurah() {
        return surah;
    }

    public void setSurah(List<Surah> surah) {
        this.surah = surah;
    }
}
