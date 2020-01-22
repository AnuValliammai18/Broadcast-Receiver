package com.example.mybroadcast

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.ImageViewCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val filter = IntentFilter()
        filter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction("android.net.conn.CONNECTIVITY_CHANGE")
            addAction("android.net.wifi.WIFI_STATE_CHANGE")
        }
        registerReceiver(MyReceiver, filter)

    }

    override fun onDestroy() {
        unregisterReceiver(MyReceiver)
        super.onDestroy()
    }
    val  MyReceiver =object: BroadcastReceiver() {

        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context, intent: Intent) {
            // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
            
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.getNetworkCapabilities(cm.activeNetwork)
            if (activeNetwork != null && activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                if (activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Toast.makeText(context, "Wifi is On", Toast.LENGTH_LONG).show()
                    wifi.setImageResource(R.drawable.wifi_connected)
                    wifi_text.text="ON"
                }
            }else {
                    Toast.makeText(context, "Wifi is Off", Toast.LENGTH_LONG).show()
                    wifi.setImageResource(R.drawable.wifi_disconnected)
                    wifi_text.text="OFF"
                }


            when {
                intent.action.equals(Intent.ACTION_POWER_CONNECTED) ->{
                    Toast.makeText(context, "Power is connected", Toast.LENGTH_LONG).show()
                    charge.setImageResource(R.drawable.charge_connected)
                    charge_text.text="ON"
                }

                    intent.action.equals(Intent.ACTION_POWER_DISCONNECTED) ->{
                    Toast.makeText(context, "Power is disconnected", Toast.LENGTH_LONG).show()
                    charge.setImageResource(R.drawable.charge_disconnected)
                    charge_text.text="OFF"
                 }

                }
            }
        }
}
