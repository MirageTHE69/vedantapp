package com.example.api.common

import com.jams.vedantattendancesystem.model.userModel
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


sealed class Resource<T>(val data:T? = null,val msg :String? =null ){
    class Success<T>(data: T?=null):Resource<T>(data)
    class Error<T>(msg: String):Resource<T>(msg = msg)
    class Loading<T>(data: T?= null):Resource<T>(data)
}