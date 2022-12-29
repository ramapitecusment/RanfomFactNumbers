package com.example.ranfomfactnumbers.numbers.domain

import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.numbers.presentation.ManageResources

interface HandleError<T> {

    fun handle(e: Exception): T

    class Base(private val manageResources: ManageResources) : HandleError<String> {

        override fun handle(e: Exception): String = manageResources.string(
            when (e) {
                is NoInternetConnectionException -> R.string.no_internet_connection_message
                else -> R.string.service_unavailable
            }
        )

    }
}