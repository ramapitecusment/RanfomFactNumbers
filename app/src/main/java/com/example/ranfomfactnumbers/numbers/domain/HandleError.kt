package com.example.ranfomfactnumbers.numbers.domain

import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.numbers.presentation.ManageResources

interface HandleError {

    fun handle(e: Exception): String

    class Base(private val manageResources: ManageResources) : HandleError {

        override fun handle(e: Exception): String = manageResources.string(
            when (e) {
                is NoInternetConnectionException -> R.string.no_internet_connection_message
                else -> R.string.service_unavailable
            }
        )

    }
}