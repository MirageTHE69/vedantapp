package com.jams.vedantattendancesystem.model

import com.google.firebase.firestore.ServerTimestamp
import java.sql.Timestamp


data class punchInModel(
    @ServerTimestamp
    val time : Timestamp,
    val punchType : String = "",
    val user_id :String ="",
    val location : String =""
)
