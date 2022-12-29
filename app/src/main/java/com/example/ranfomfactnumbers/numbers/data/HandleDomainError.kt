package com.example.ranfomfactnumbers.numbers.data

import com.example.ranfomfactnumbers.numbers.domain.HandleError
import com.example.ranfomfactnumbers.numbers.domain.NoInternetConnectionException
import com.example.ranfomfactnumbers.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {

    override fun handle(e: Exception) = when (e) {
        is UnknownHostException -> NoInternetConnectionException()
        else -> ServiceUnavailableException()
    }

}