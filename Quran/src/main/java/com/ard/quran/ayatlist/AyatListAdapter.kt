package com.ard.quran.ayatlist

import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.ard.quran.model.ayat.Ayat
import com.ard.quran.model.ayat.AyatModel
import com.ard.quran.tajweed.exporter.Exporter
import com.ard.quran.tajweed.exporter.HtmlExporter
import com.ard.quran.tajweed.model.Result
import com.ard.quran.tajweed.model.ResultUtil
import com.ard.quran.tajweed.model.TajweedRule
import com.ard.quran.R
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
                    .replace("1".toRegex(), "??").replace("2".toRegex(), "??")
                    .replace("3".toRegex(), "??").replace("4".toRegex(), "??")
                    .replace("5".toRegex(), "??").replace("6".toRegex(), "??")
                    .replace("7".toRegex(), "??").replace("8".toRegex(), "??")
                    .replace("9".toRegex(), "??").replace("0".toRegex(), "??")
        }
    }


}