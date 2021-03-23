package com.ard.quran.tajweed

import com.ard.quran.tajweed.exporter.Exporter
import com.ard.quran.tajweed.exporter.TextExporter
import com.ard.quran.tajweed.model.Result
import com.ard.quran.tajweed.model.ResultUtil
import com.ard.quran.tajweed.model.TajweedRule
import java.util.*

object TajweedRules {
    @JvmStatic
    fun main(args: Array<String>) {

        // text uses uthmani text from tanzil
        val text = arrayOf(
            "ٱلْحَمْدُ لِلَّـهِ رَبِّ ٱلْعَـٰلَمِينَ",  // 1:2
            "الٓمٓ",  // 2:1
            "ذَٰلِكَ ٱلْكِتَـٰبُ لَا رَيْبَ ۛ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ",  // 2:2
            "ثُمَّ بَعَثْنَـٰكُم مِّنۢ بَعْدِ مَوْتِكُمْ لَعَلَّكُمْ تَشْكُرُونَ",  // 2:56
            "كٓهيعٓصٓ",  // 19:1
            " مَا يُجَـٰدِلُ فِىٓ ءَايَـٰتِ ٱللَّـهِ إِلَّا ٱلَّذِينَ كَفَرُوا۟ فَلَا يَغْرُرْكَ تَقَلُّبُهُمْ فِى ٱلْبِلَـٰدِ",  // 40:4
            "تَرْمِيهِم بِحِجَارَةٍ مِّن سِجِّيلٍ",  // 105:4
            "تَبَّتْ يَدَآ أَبِى لَهَبٍ وَتَبَّ"
        )
        val exporter: Exporter = TextExporter()
        exporter.onOutputStarted()
        val rules = TajweedRule.MADANI_RULES
        for (ayahText in text) {
            val results: MutableList<Result> = ArrayList()
            for (tajweedRule in rules) {
                results.addAll(tajweedRule.rule.checkAyah(ayahText))
            }
            ResultUtil.INSTANCE.sort(results)
            exporter.export(ayahText, results)
        }
        exporter.onOutputCompleted()
    }
}