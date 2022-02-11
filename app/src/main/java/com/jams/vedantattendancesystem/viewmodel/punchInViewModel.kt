package com.jams.vedantattendancesystem.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.example.api.common.Resource
import com.jams.vedantattendancesystem.model.punchInModel
import com.jams.vedantattendancesystem.repository.PunchInRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import androidx.lifecycle.*
import kotlinx.coroutines.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO

class punchInViewModel(application: Application) : AndroidViewModel(application) {
    val repository = PunchInRepo()

    init {

    }

    fun createPunch(punchInModel: punchInModel) {

        Log.d(TAG, "createPunch: Reached createPunchMethod")
        viewModelScope.launch(IO) {
            Log.d(TAG, "createPunch: Reached viewModelScope")
            val result = repository.createPunch(punchInModel);
            Log.d(TAG, "createPunch: Reached repo")
            when (result) {
                is Resource.Success -> {

                }
                is Resource.Error -> {
                    Log.d(TAG, "createPunch: ${result.msg}")
                    // emit(Resource.Error( result.msg!!) )
                }
                is Resource.Loading -> {
                    //  emit(Resource.Loading())
                }

            }
        }
    }
}



