package com.ard.dailyislam.main

import android.content.Intent
import android.icu.util.IslamicCalendar
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ard.dailyislam.R
import com.ard.jadwal.JadwalSholat
import com.ard.quran.QuranView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        startActivity(JadwalSholat::class.java)
        sholatIcon goTo JadwalSholat::class.java
        quranIcon goTo QuranView::class.java
        getDate()
    }


    private fun getDate() {

        val calendar = Calendar.getInstance()
        val date = calendar.time

        val localeIndonesia = Locale("id", "ID")
        val hari = SimpleDateFormat("EEEE, dd MMMM", localeIndonesia).format(Date())

        tanggalMasehi.text = hari

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val islam =   IslamicCalendar.getInstance()
            val hariIslam = islam.time
            tanggalIslam.text = hariIslam.toString()

        } else {

        }

    }

    private fun getIslamDate(){

    }

    private fun startActivity(cls: Class<*>){
        startActivity(Intent(this, cls))
    }

    private infix fun View.goTo(cls: Class<*>) {
        setOnClickListener { startActivity(cls) }
    }
}