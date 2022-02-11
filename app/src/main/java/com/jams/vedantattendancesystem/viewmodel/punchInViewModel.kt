package com.jams.vedantattendancesystem.viewmodel

import com.example.api.common.Resource
import com.jams.vedantattendancesystem.model.punchInModel
import com.jams.vedantattendancesystem.repository.PunchInRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import androidx.lifecycle.*
import kotlinx.coroutines.*

class punchInViewModel {
    val repository  = PunchInRepo()
init {

}
fun createPunch(punchInModel: punchInModel):Flow<Resource<Boolean>> = flow<Resource<Boolean>> {
        emit(Resource.Loading())

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