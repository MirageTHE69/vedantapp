package com.jams.vedantattendancesystem.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.api.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jams.vedantattendancesystem.model.punchInModel
import kotlin.math.log

class PunchInRepo {
    private val firebaseFirestore = FirebaseFirestore.getInstance().collection("Punch_table")
    val firebaseAuth = FirebaseAuth.getInstance()


    suspend fun createPunch(punchInModel: punchInModel):Resource<Boolean>{

        return try {

            Log.d(TAG, "createPunch: Reached repo")
            firebaseFirestore.whereEqualTo("user_id",punchInModel.user_id).whereEqualTo("timestamp",punchInModel.time).get().addOnSuccessListener {
               if(it.documents.size>1){
                   throw ExceedPunchException("you Already punch in punch out")

               } else if (it.documents.isNotEmpty()) {
                   if(it.documents[0]["type"]==punchInModel.punchType){
                       throw ExceedPunchException("You already punch ${punchInModel.punchType}")
                   }
               }
            }
            val id= firebaseAuth.currentUser!!.uid
            val punchInM = punchInModel(user_id = id, punchType = punchInModel.punchType, location = punchInModel.location)
            firebaseFirestore.add(punchInM).addOnSuccessListener {
            }
            Resource.Success()
        }catch (e:Exception){
            Resource.Error(msg = e.localizedMessage)
        }
    }

}

class ExceedPunchException(message: String) : Exception(message)