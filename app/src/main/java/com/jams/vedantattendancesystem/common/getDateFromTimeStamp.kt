package com.jams.vedantattendancesystem.common

import com.google.firebase.Timestamp
import com.google.type.Date

fun Timestamp.getDateFromTimeStamp(): Date {
    //February 5, 2022 at 12:56:27 AM UTC+5:30
    return Date.getDefaultInstance()
}