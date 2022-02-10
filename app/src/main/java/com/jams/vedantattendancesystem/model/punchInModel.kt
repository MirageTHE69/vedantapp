package com.jams.vedantattendancesystem.model

import java.sql.Timestamp

data class punchInModel(

    var time : Timestamp,
    var punchIn : Boolean = false

)
