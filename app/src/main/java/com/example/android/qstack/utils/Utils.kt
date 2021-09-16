package com.example.android.qstack.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.android.qstack.MainActivity
import com.example.android.qstack.R
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*

const val LINK_KEY = "link"

const val FILTER_QUESTION = "!*MZqiH2o(Vpewl5z"
const val FILTER_USER = "!LotZkgdlNRUNxK.2HyfS8j"

enum class OrderBY(val order : String){
    DESC("desc"), ASC("asc")
}

enum class SortBy(val sortOrder : String){
    ACTIVITY("activity"), CREATION("creation"),
    POPULAR("popular"), REPUTATION("reputation")
}


fun Long.convertEpochDateToTime() : String{
    val dateNow = Date()
    val timeNow = dateNow.time / 1000
    val seconds = timeNow - this

    return when{
        //Date is more than 2days
        seconds > (2*24*3600)->{
            "Few days ago"
        }
        //Date is more than a day
        seconds > (24*3600)->{
            "Yesterday"
        }
        seconds > 3600 ->{
            "${seconds/3600} hours ago"
        }
        seconds > 1800 ->{
            "Half an hour ago"
        }
        else ->{
            "${seconds/60} minutes ago"
        }
    }
}


@RequiresApi(Build.VERSION_CODES.N)
fun MainActivity.displaySnackBar(){
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let {
        it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                val view = findViewById<CoordinatorLayout>(R.id.coordinator)
                Snackbar.make(view, "You are offline. Data displayed is a cached version",
                    Snackbar.LENGTH_INDEFINITE).apply {
                    setAction("DISMISS") {
                        dismiss()
                    }
                    show()
                }
            }

            override fun onAvailable(network: Network) {
                Toast.makeText(this@displaySnackBar, "Network is back", Toast.LENGTH_LONG).show()
            }
        })
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun isInternetOnline(): Boolean{
    val site = "ping -c 1 google.com"
    return Runtime.getRuntime().exec(site).waitFor() == 0
}

fun isOnline(context: Context): Boolean {
    val connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    } else {
        val info = connectivityManager.activeNetworkInfo ?: return false
        return info.isConnectedOrConnecting
    }
}

fun isOnlineSocket(): Boolean{
    val isOnline: Boolean = try {
        val socket = Socket()
        socket.connect(InetSocketAddress("8.8.8.8", 53), 3000)
        true
    }catch (exception: Exception){
        Timber.d(exception)
        false
    }
    return isOnline

}