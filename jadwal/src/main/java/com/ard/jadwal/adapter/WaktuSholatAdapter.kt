package com.ard.jadwal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ard.jadwal.R
import com.ard.jadwal.model.WaktuSholat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.single_waktu_sholat.view.*


class WaktuSholatAdapter : RecyclerView.Adapter<WaktuSholatAdapter.ViewHolder>(){
     val waktuList = mutableListOf<WaktuSholat>()
     lateinit var namaSelanjutnya:String
     lateinit var waktuSelanjutnya:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_waktu_sholat, parent, false)
        )

    override fun getItemCount() = waktuList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(waktuList[position],namaSelanjutnya,waktuSelanjutnya)

    }
//
    fun updateData(newWaktuSholat: MutableList<WaktuSholat>, namaSelanjutnya: String, waktuSelanjutnya: String) {
        this.namaSelanjutnya = namaSelanjutnya
        this.waktuSelanjutnya = waktuSelanjutnya
        waktuList.clear()
        waktuList.addAll(newWaktuSholat)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = itemView
        fun bindItem(item: WaktuSholat, namaSelanjutnya: String, waktuSelanjutnya: String) {

            itemView.nama.text = item.nama
            itemView.waktu.text = item.waktu

            if (item.nama.equals(namaSelanjutnya)){
                itemView.viewMenuju.visibility= View.VISIBLE
                itemView.textMenuju.text = waktuSelanjutnya
                itemView.nama.textSize = 30.0F
                itemView.nama.setTextColor(Color.parseColor("#FFFFFF"))
                itemView.waktu.setTextColor(Color.parseColor("#FFFFFF"))
                itemView.textMenuju.setTextColor(Color.parseColor("#FFFFFF"))
                itemView.iconAlarm.setColorFilter(Color.parseColor("#FFFFFF"))
                itemView.parentView.setBackgroundResource(R.drawable.bg_list_sholat_active)
            }
//            itemView.noSurahTxt.text = ""+item.nomor
//            itemView.namaSurah.text = item.nama
//            itemView.arabSurah.text = item.asma






        }
    }


}