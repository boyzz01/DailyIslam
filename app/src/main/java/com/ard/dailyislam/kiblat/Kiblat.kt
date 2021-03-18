package com.ard.dailyislam.kiblat

import android.content.Context
import android.content.SharedPreferences
import android.hardware.*
import android.location.Location
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.ard.dailyislam.R
import kotlinx.android.synthetic.main.activity_kiblat.*

class Kiblat : AppCompatActivity() , SensorEventListener {

    private var currentDegree = 0f
    private var currentDegreeNeedle = 0f
    var userLoc = Location("service Provider")

    companion object {
        private var sensorManager: SensorManager? = null
        private var sensor: Sensor? = null

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kiblat)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)


        if (sensor != null) {
            sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        } else {
            Toast.makeText(applicationContext, "Not Support", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val degree = Math.round(sensorEvent.values[0])
        var head = Math.round(sensorEvent.values[0]).toFloat()
        var bearTo: Float
        val destinationLoc = Location("service Provider")
        destinationLoc.latitude = 21.422487 //kaaba latitude setting
        destinationLoc.longitude = 39.826206 //kaaba longitude setting
        bearTo = userLoc.bearingTo(destinationLoc)
        //bearTo = The angle from true north to the destination location from the point we're your currently standing.(asal image k N se destination taak angle )
        //head = The angle that you've rotated your phone from true north. (jaise image lagi hai wo true north per hai ab phone jitne rotate yani jitna image ka n change hai us ka angle hai ye)
        val geoField = GeomagneticField(
            java.lang.Double.valueOf(userLoc.latitude).toFloat(), java.lang.Double
                .valueOf(userLoc.longitude).toFloat(),
            java.lang.Double.valueOf(userLoc.altitude).toFloat(),
            System.currentTimeMillis()
        )
        head -= geoField.declination // converts magnetic north into true north
        if (bearTo < 0) {
            bearTo = bearTo + 360
            //bearTo = -100 + 360  = 260;
        }
        var direction = bearTo + head

        // If the direction is smaller than 0, add 360 to get the rotatigon clockwise.
        if (direction < 0) {
            direction = direction + 360
        }
        val raQibla = RotateAnimation(
            currentDegreeNeedle, direction,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        raQibla.duration = 500
        raQibla.fillAfter = true
        currentDegreeNeedle = -direction
        jarum_kiblat.startAnimation(raQibla)
        val animation = RotateAnimation(
            currentDegree, (-degree).toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 500
        animation.fillAfter = true
        ic_compass!!.animation = animation
        currentDegree = -degree.toFloat()
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) {
    } //    public void adjustArrowQiblat(float azimuth) {
    //        Animation an = new RotateAnimation(-(currentAzimuth)+kiblat_derajat, -azimuth,
    //                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
    //                0.5f);
    //        currentAzimuth = (azimuth);
    //        an.setDuration(500);
    //        an.setRepeatCount(0);
    //        an.setFillAfter(true);
    //        arrowViewQiblat.startAnimation(an);
    //
    //    }
    //
    //    public void arrowkiblat(){
    //        Location kaabaLocation = new Location("");
    //        kaabaLocation.setLatitude(21.4225);
    //        kaabaLocation.setLongitude(39.8262);
    //        float kaabaBearing = userLocation.bearingTo(kaabaLocation);
    //    }
}