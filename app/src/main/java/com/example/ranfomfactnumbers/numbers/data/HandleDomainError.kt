package com.example.ranfomfactnumbers.numbers.data

import android.util.Log
import com.example.ranfomfactnumbers.numbers.domain.HandleError
import com.example.ranfomfactnumbers.numbers.domain.NoInternetConnectionException
import com.example.ranfomfactnumbers.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {

    override fun handle(e: Exception): java.lang.Exception {
        Log.e(this::class.java.simpleName, e.toString())
        return when (e) {
            is UnknownHostException -> NoInternetConnectionException()
            else -> ServiceUnavailableException()
        }
    }

}