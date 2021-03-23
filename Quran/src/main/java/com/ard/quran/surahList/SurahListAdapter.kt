package com.ard.quran.surahList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ard.quran.R

import com.ard.quran.model.surah.Surah
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.single_surah_layout.view.*

class SurahListAdapter : RecyclerView.Adapter<SurahListAdapter.ViewHolder>(){
     val surahList = mutableListOf<Surah>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_surah_layout, parent, false)
        )

    override fun getItemCount() = surahList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(surahList[position])

    }

    fun updateData(newSurahList : MutableList<Surah>) {
        surahList.clear()
        surahList.addAll(newSurahList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = itemView
        fun bindItem(item: Surah) {
            itemView.noSurahTxt.text = ""+item.nomor
            itemView.namaSurah.text = item.nama
            itemView.arabSurah.text = item.asma



            val type: String = item.type.substring(0, 1).toUpperCase() + item.type.substring(1)
            itemView.detailSurah.text =
                type + " - " + item.arti + " - " + item.ayat + " Ayat"


            containerView?.setOnClickListener{view ->
                val action =
                    SurahListFragmentDirections.actionSurahListFragmentToAyatListFragment(item,""+item.nomor)
                view.findNavController().navigate(action)
            }
        }
    }

    fun filter(surahCopy: List<Surah>,query : String?) {

        surahList.clear()

        for (data in surahCopy){
            if (query!!.isEmpty()){
                surahList.addAll(surahCopy)
            }else{
                if (data.nama.toLowerCase().contains(query.toLowerCase())){
                    surahList.add(data)
                }
            }
        }
        notifyDataSetChanged()
    }
}