package com.ard.dailyislam.quran.ayatlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ard.dailyislam.R
import com.ard.dailyislam.quran.model.ayat.Ayat
import com.ard.dailyislam.quran.model.ayat.AyatModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.single_ayat_layout.view.*


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

    fun updateData(no : String,ayat : Ayat) {
        ayatList.clear()
        ayatList.addAll(ayat.ayatList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = itemView
        fun bindItem(item: AyatModel) {
            itemView.noTxt.text = ""+item.no+"."
            itemView.ayatTxt.text = item.arabicString
            itemView.artiTxt.text = item.artiString



            containerView?.setOnClickListener{view ->
//                val action =
//                    SurahListFragmentDirections.actionSurahListFragmentToAyatListFragment(item.nomor)
//                view.findNavController().navigate(action)
            }
        }
    }
}