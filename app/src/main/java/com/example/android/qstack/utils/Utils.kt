package com.example.android.qstack.utils

import java.util.*

enum class OrderBY(val order : String){
    DESC("desc"), ASC("asc")
}

enum class SortBy(val sortOrder : String){
    ACTIVITY("activity"), CREATION("creation"), VOTES("votes")
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
