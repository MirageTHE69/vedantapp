package com.jams.vedantattendancesystem.repository

import com.example.api.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class getPointsRepo {

    private val firebaseFirestore = FirebaseFirestore.getInstance().collection("Punch_table")
    val firebaseAuth = FirebaseAuth.getInstance()


    suspend fun getPointsByMonth(user_id:String,month:Int):Resource<Int> {
        return try {


            val Startdate = Date("2022-$month-01")
            val enddate = Date("2022-$month-30")
            var count = 0
            firebaseFirestore.whereEqualTo("user_id", user_id).whereEqualTo("type", "IN")
                .whereGreaterThan("timestamp", Startdate).whereLessThan("timestamp", enddate).get()
                .addOnSuccessListener {
                    count = it.documents.size
                }
            Resource.Success(count)
        }catch (e:Exception){
            Resource.Error(e.localizedMessage)
        }
    }
}