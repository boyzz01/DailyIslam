package com.ard.jadwal

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ard.jadwal.adapter.WaktuSholatAdapter
import com.ard.jadwal.model.WaktuSholat
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_jadwal_sholat.*
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class JadwalSholat : AppCompatActivity() {


    private lateinit var waktuSholatList : MutableList<WaktuSholat>
    private lateinit var adapter : WaktuSholatAdapter

    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var namaSelanjutnya : String
    lateinit var waktuSelanjutnya : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal_sholat)


        waktuSholatList = ArrayList()

        adapter = WaktuSholatAdapter()
        listWaktu.adapter = adapter
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        init()
        getLastLocation()

    }

    private fun init() {
        val df = SimpleDateFormat("dd MMMM yyyy", Locale("id","ID"))
        val formattedDate = df.format(Date())
        tanggal.text = formattedDate
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        Log.d(
                                "My Current location",
                                "Lat : ${location?.latitude} Long : ${location?.longitude}"
                        )
                        // Display in Toast

                        getNamaLokasi(location?.latitude, location?.longitude)
                        getWaktu(location?.latitude, location?.longitude)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            Log.d(
                    "My Current location 2",
                    "Lat : ${mLastLocation?.latitude} Long : ${mLastLocation?.longitude}"
            )
            getNamaLokasi(mLastLocation?.latitude, mLastLocation.longitude)
            getWaktu(mLastLocation?.latitude, mLastLocation.longitude)
        }
    }

    private fun getWaktu(latitude: Double, longitude: Double) {
        val coordinates = Coordinates(latitude, longitude)
        val date = DateComponents.from(Date())
        val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
   
        val prayerTimes = PrayerTimes(coordinates, date, params)


        val formatter = SimpleDateFormat("HH:mm")
        val imsak = formatter.parse(formatter.format((prayerTimes.fajr)))
        val cal = Calendar.getInstance()
        cal.time = imsak
        cal.add(Calendar.MINUTE, -10)
        val newTime: String = formatter.format(cal.time)
        System.out.println("Fajr: " + formatter.format((prayerTimes.fajr)))
        System.out.println("Sunrise: " + formatter.format(prayerTimes.sunrise))
        System.out.println("Dhuhr: " + formatter.format(prayerTimes.dhuhr))
        System.out.println("Asr: " + formatter.format(prayerTimes.asr))
        System.out.println("Maghrib: " + formatter.format(prayerTimes.maghrib))
        System.out.println("Isha: " + formatter.format(prayerTimes.isha))

        waktuSholatList.add(WaktuSholat("Imsak", newTime, false))
        waktuSholatList.add(WaktuSholat("Subuh", formatter.format(prayerTimes.fajr), false))
        waktuSholatList.add(WaktuSholat("Matahari Terbit", formatter.format(prayerTimes.sunrise), false))
        waktuSholatList.add(WaktuSholat("Dzuhur", formatter.format(prayerTimes.dhuhr), false))
        waktuSholatList.add(WaktuSholat("Ashar", formatter.format(prayerTimes.asr), false))
        waktuSholatList.add(WaktuSholat("Magrib", formatter.format(prayerTimes.maghrib), false))
        waktuSholatList.add(WaktuSholat("Isya", formatter.format(prayerTimes.isha), false))

        for (i in 0 until waktuSholatList.size){

            if (i<6){

                getWaktuSelanjutnya(waktuSholatList.get(i + 1).nama, waktuSholatList.get(i).waktu, waktuSholatList.get(i + 1).waktu)
            }else{

                getWaktuSelanjutnya(waktuSholatList.get(0).nama, waktuSholatList.get(i).waktu, waktuSholatList.get(0).waktu)
            }


        }

        adapter.updateData(waktuSholatList, namaSelanjutnya, waktuSelanjutnya)
    }

    private fun getWaktuSelanjutnya(nama1: String, waktu: String, waktu1: String) {
        try {
            val sdf = SimpleDateFormat("HH:mm")
            val string1 = waktu
            val time1 = sdf.parse(string1)
            val string2 = waktu1
            val time2 = sdf.parse(string2)
            val date = sdf.format(Date())
            val x = sdf.parse(date)
           //Log.d("disini waktu", nama1 + " " + time1 + " " + time2 + " " + x)
        //    if (date.after(time1))
            if (x.after(time1) && x.before(time2)) {

                val diffMs: Long = time2.getTime() - x.getTime()
                val diffHours: Long = diffMs / (60 * 60 * 1000)% 24
                val diffMinutes: Long = diffMs / (60 * 1000) % 60
                namaSelanjutnya = nama1
                waktuSelanjutnya = ""+diffHours+" Jam "+diffMinutes+" Menit lagi menuju "+nama1

            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }


    private fun getNamaLokasi(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        try {
            val listAddresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            if (null != listAddresses && listAddresses.size > 0) {
                val Lokasi: String = listAddresses[0].locality
                alamat.text = Lokasi
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
}