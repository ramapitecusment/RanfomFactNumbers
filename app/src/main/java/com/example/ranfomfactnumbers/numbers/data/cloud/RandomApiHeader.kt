package com.example.ranfomfactnumbers.numbers.data.cloud

import okhttp3.Headers
import okhttp3.Headers.Companion.toHeaders
import retrofit2.Response

interface RandomApiHeader {

    fun findInHeader(headers: Headers): Pair<String, String>?

    interface MockResponse {

        fun makeResponse(body: String, headerValue: String): Response<String>

    }

    interface Combo : RandomApiHeader, MockResponse

    abstract class Abstract(private val header: String) : Combo {

        override fun findInHeader(headers: Headers) = headers.find { (key, _) -> header == key }

        override fun makeResponse(body: String, headerValue: String) = Response.success(
            body, mapOf(header to headerValue).toHeaders()
        )
    }

    class Base: Abstract(RANDOM_API_HEADER)
    class Mock(value: String = RANDOM_API_HEADER_MOCK) : Abstract(value)

    companion object {
        private const val RANDOM_API_HEADER = "X-Numbers-API-Number"
        private const val RANDOM_API_HEADER_MOCK = "X-Numbers-API-Number-Mock"
    }

}