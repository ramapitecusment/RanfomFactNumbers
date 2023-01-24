package com.example.ranfomfactnumbers.numbers.domain

import android.util.Log
import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.numbers.presentation.ManageResources

interface HandleError<T> {

    fun handle(e: Exception): T

    class Base(private val manageResources: ManageResources) : HandleError<String> {

        override fun handle(e: Exception): String {
            Log.e(this::class.java.simpleName, e.toString())
            val result = when (e) {
                is NoInternetConnectionException -> R.string.no_internet_connection_message
                else -> R.string.service_unavailable
            }
            return manageResources.string(result)
        }

    }
}