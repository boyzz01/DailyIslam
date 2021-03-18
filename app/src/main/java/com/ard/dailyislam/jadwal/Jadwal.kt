package com.ard.dailyislam.jadwal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.ard.dailyislam.R
import com.ard.dailyislam.quran.tajweed.exporter.Exporter
import com.ard.dailyislam.quran.tajweed.exporter.HtmlExporter
import com.ard.dailyislam.quran.tajweed.model.Result
import com.ard.dailyislam.quran.tajweed.model.ResultUtil
import com.ard.dailyislam.quran.tajweed.model.TajweedRule
import kotlinx.android.synthetic.main.activity_jadwal.*
import java.util.ArrayList

class Jadwal : AppCompatActivity() {

    val results: MutableList<Result> = ArrayList()
    val text = arrayOf(
        " ۞ اَللّٰهُ نُوْرُ السَّمٰوٰتِ وَالْاَرْضِۗ مَثَلُ نُوْرِهٖ كَمِشْكٰوةٍ فِيْهَا مِصْبَاحٌۗ  اَلْمِصْبَاحُ فِيْ زُجَاجَةٍۗ  اَلزُّجَاجَةُ كَاَنَّهَا كَوْكَبٌ دُرِّيٌّ يُّوْقَدُ مِنْ شَجَرَةٍ مُّبٰرَكَةٍ زَيْتُوْنَةٍ لَّا شَرْقِيَّةٍ وَّلَا غَرْبِيَّةٍۙ يَّكَادُ زَيْتُهَا يُضِيْۤءُ وَلَوْ لَمْ تَمْسَسْهُ نَارٌۗ  نُوْرٌ عَلٰى نُوْرٍۗ يَهْدِى اللّٰهُ لِنُوْرِهٖ مَنْ يَّشَاۤءُۗ وَيَضْرِبُ اللّٰهُ الْاَمْثَالَ لِلنَّاسِۗ وَاللّٰهُ بِكُلِّ شَيْءٍ عَلِيْمٌ ٌَ"  // 1:2
   // 2:1

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        val exporter: Exporter = HtmlExporter()
        val rules = TajweedRule.MADANI_RULES

        for (ayahText in text) {

            for (tajweedRule in rules) {
                results.addAll(tajweedRule.rule.checkAyah(ayahText))
            }
            ResultUtil.INSTANCE.sort(results)
           val a = exporter.export(ayahText, results)
            tes.text=Html.fromHtml(a)
        }
        exporter.onOutputCompleted()


    }


}