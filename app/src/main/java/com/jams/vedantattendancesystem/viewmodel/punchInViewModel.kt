package com.jams.vedantattendancesystem.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.example.api.common.Resource
import com.jams.vedantattendancesystem.model.punchInModel
import com.jams.vedantattendancesystem.repository.PunchInRepo
import androidx.lifecycle.*
import kotlinx.coroutines.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class punchInViewModel(application: Application) : AndroidViewModel(application) {
    val repository = PunchInRepo()
    private val punchEventChannel = Channel<CurrentEvent>()
    val punchEventFlow =punchEventChannel.receiveAsFlow()

    init {

    }

    fun createPunch(punchInModel: punchInModel) {

        Log.d(TAG, "createPunch: Reached createPunchMethod")
        viewModelScope.launch(IO) {
            punchEventChannel.send(CurrentEvent.Loading)
            Log.d(TAG, "createPunch: Reached viewModelScope")
            val result = repository.createPunch(punchInModel);
            Log.d(TAG, "createPunch: Reached repo")
            when (result) {
                is Resource.Success -> {
                    Log.d(TAG, "createPunch: Success")
                    delay(3000)
                    punchEventChannel.send(CurrentEvent.Success(""))
                }
                is Resource.Error -> {
                    Log.d(TAG, "createPunch: ${result.msg}")
                    punchEventChannel.send(CurrentEvent.Failure(result.msg!!))
                }
                is Resource.Loading -> {

                }

            }
        }
    }
}



sealed class CurrentEvent {

    class  Success<T>(val result :T) : CurrentEvent()
    class  Failure(val errorText : String) : CurrentEvent()

    object Loading: CurrentEvent()
    object Empty : CurrentEvent()
}