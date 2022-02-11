package com.jams.vedantattendancesystem.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp



data class punchInModel(
    @ServerTimestamp
    val time : Timestamp?=null,
    val punchType : String = "",
    val user_id :String ="",
    val location : String =""
)
