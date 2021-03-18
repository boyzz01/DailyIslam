package com.ard.dailyislam.quran.ayatlist

import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ard.dailyislam.R
import com.ard.dailyislam.quran.model.ayat.Ayat
import com.ard.dailyislam.quran.model.ayat.AyatModel
import com.ard.dailyislam.quran.tajweed.exporter.Exporter
import com.ard.dailyislam.quran.tajweed.exporter.HtmlExporter
import com.ard.dailyislam.quran.tajweed.model.Result
import com.ard.dailyislam.quran.tajweed.model.ResultUtil
import com.ard.dailyislam.quran.tajweed.model.TajweedRule
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.single_ayat_layout.view.*
import java.util.*


class AyatListAdapter(nomor: String) : RecyclerView.Adapter<AyatListAdapter.ViewHolder>(){
     val ayatList = mutableListOf<AyatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.single_ayat_layout, parent, false)
        )

    override fun getItemCount() = ayatList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(ayatList[position])

    }

    fun updateData(no: String, ayat: Ayat) {
        ayatList.clear()
        ayatList.addAll(ayat.ayatList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun bindItem(item: AyatModel) {
            itemView.noTxt.text = ""+item.no+". "


            val upperString: String =
                item.artiString.substring(0, 1).toUpperCase() + item.artiString.substring(1).toLowerCase()
            itemView.artiTxt.text = upperString


           // val number = "  "+"&#xFD3F;"+ convertToArabic(item.no)+"&#xFD3E;"
             val number = ""+ convertToArabic(item.no)

            val results: MutableList<Result> = ArrayList()
            val tempText = item.arabicString.replace("\u08D6","")
            val text = arrayOf(
                    item.arabicString
            )

            val exporter: Exporter = HtmlExporter()
            val rules = TajweedRule.MADANI_RULES

            for (ayahText in text) {

                for (tajweedRule in rules) {
                    results.addAll(tajweedRule.rule.checkAyah(ayahText))
                }
                ResultUtil.INSTANCE.sort(results)
                val a = exporter.export(ayahText, results)+number
                itemView.ayatTxt.text = Html.fromHtml(a)
            }
            exporter.onOutputCompleted()

            containerView?.setOnClickListener{ view ->
//                val action =
//                    SurahListFragmentDirections.actionSurahListFragmentToAyatListFragment(item.nomor)
//                view.findNavController().navigate(action)
            }
        }
        fun convertToArabic(value: Int): String? {
            return (value.toString() + "")
                    .replace("1".toRegex(), "١").replace("2".toRegex(), "٢")
                    .replace("3".toRegex(), "٣").replace("4".toRegex(), "٤")
                    .replace("5".toRegex(), "٥").replace("6".toRegex(), "٦")
                    .replace("7".toRegex(), "٧").replace("8".toRegex(), "٨")
                    .replace("9".toRegex(), "٩").replace("0".toRegex(), "٠")
        }
    }


}