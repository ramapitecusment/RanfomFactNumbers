package com.example.ranfomfactnumbers.numbers.data.cloud

class MockNumbersService(
    private val randomApiHeader: RandomApiHeader.MockResponse
) : NumbersService {

    override suspend fun fact(id: String) = "fact about $id"

    private var count = 0

    override suspend fun random() = randomApiHeader.makeResponse("fact about ${++count}", count.toString())

}