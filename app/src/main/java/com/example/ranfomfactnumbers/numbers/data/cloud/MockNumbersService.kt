package com.example.ranfomfactnumbers.numbers.data.cloud

import okhttp3.Headers.Companion.toHeaders
import retrofit2.Response

class MockNumbersService : NumbersService {

    override suspend fun fact(id: String): String = "fact about $id"

    private var count = 0

    override suspend fun random(): Response<String> = Response.success(
        "fact about ${++count}",
        mapOf("X-Numbers-API-Number" to count.toString()).toHeaders()
    )

}