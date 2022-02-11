package com.jams.vedantattendancesystem.viewmodel

import android.app.Application
import com.example.api.common.Resource
import com.jams.vedantattendancesystem.model.punchInModel
import com.jams.vedantattendancesystem.repository.PunchInRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import androidx.lifecycle.*
import kotlinx.coroutines.*
import androidx.lifecycle.viewModelScope

class punchInViewModel(application: Application) : AndroidViewModel(application) {
    val repository  = PunchInRepo()
init {

}
fun createPunch(punchInModel: punchInModel):Flow<Resource<Boolean>> = flow<Resource<Boolean>> {
        emit(Resource.Loading())

    viewModelScope.launch{

        val result =repository.createPunch(punchInModel);
        when(result){
            is Resource.Success -> {
                emit(Resource.Success())
            }
            is Resource.Error -> {
                emit(Resource.Error( result.msg!!) )
            }
            is Resource.Loading -> {
                emit(Resource.Loading())
            }

        }
    }

}
}