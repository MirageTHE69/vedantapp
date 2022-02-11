package com.example.api.common

import com.jams.vedantattendancesystem.model.userModel
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


sealed class Resource<T>(val data: Any? = null, val message: String? = null) {

    class Success<T>(data: Response<List<userModel>>) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)

}