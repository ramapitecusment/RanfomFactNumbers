package com.example.ranfomfactnumbers.numbers.data

import com.example.ranfomfactnumbers.numbers.domain.NoInternetConnectionException
import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class NumbersRepositoryTest {

    private lateinit var repository: NumbersRepository
    private lateinit var cloudDataSource: TestNumbersCloudDataSource
    private lateinit var cacheDataSource: TestNumbersCacheDataSource

    @Before
    fun setup() {
        cloudDataSource = TestNumbersCloudDataSource()
        cacheDataSource = TestNumbersCacheDataSource()
        val mapper = NumberDataToDomain()
        repository = NumbersRepository.Base(
            HandleDataRequest.Base(HandleDomainError(), cacheDataSource, mapper),
            cloudDataSource,
            cacheDataSource,
            mapper
        )
    }

    @Test
    fun test_all_numbers() = runBlocking {
        cacheDataSource.replaceData(
            listOf(
                NumberData("4", "fact of 4"),
                NumberData("5", "fact of 5"),
            )
        )

        val expected = listOf(
            NumberFact("4", "fact of 4"),
            NumberFact("5", "fact of 5"),
        )
        val actual = repository.allNumbers()

        actual.forEachIndexed { index, numberData ->
            assertEquals(expected[index], numberData)
        }
        assertEquals(1, cacheDataSource.allNumbersCalledCount)
    }

    @Test
    fun test_number_fact_not_cached_success() = runBlocking {
        cloudDataSource.makeExpected(NumberData("10", "fact about 10"))
        cacheDataSource.replaceData(emptyList())

        val expected = NumberFact("10", "fact about 10")
        val actual = repository.numberFact("10")

        assertEquals(expected, actual)
        assertEquals(false, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(1, cloudDataSource.numberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(NumberData("10", "fact about 10"), cacheDataSource.data[0])
    }

    @Test
    fun test_number_fact_not_cached_failure() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        try {
            val actual = repository.numberFact("10")
        } catch (e: NoInternetConnectionException) {
            assertEquals(false, cacheDataSource.containsCalledList[0])
            assertEquals(1, cacheDataSource.containsCalledList.size)
            assertEquals(1, cloudDataSource.numberFactCalledCount)
            assertEquals(0, cacheDataSource.numberFactCalledList.size)
            assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
        }
    }

    @Test
    fun test_number_fact_cached() = runBlocking {
        cloudDataSource.makeExpected(NumberData("10", "cloud 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val expected = NumberFact("10", "fact 10")
        val actual = repository.numberFact("10")

        assertEquals(expected, actual)
        assertEquals(true, cacheDataSource.containsCalledList[0])
        assertEquals(1, cacheDataSource.containsCalledList.size)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    @Test
    fun test_random_fact_not_cached_success() = runBlocking {
        cloudDataSource.makeExpected(NumberData("10", "fact about 10"))
        cacheDataSource.replaceData(emptyList())

        val expected = NumberFact("10", "fact about 10")
        val actual = repository.randomNumberFact()

        assertEquals(expected, actual)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
        assertEquals(NumberData("10", "fact about 10"), cacheDataSource.data[0])
    }


    @Test
    fun test_random_fact_not_cached_failure() = runBlocking {
        cloudDataSource.changeConnection(false)
        cacheDataSource.replaceData(emptyList())

        try {
            val actual = repository.randomNumberFact()
        } catch (e: NoInternetConnectionException) {
            assertEquals(0, cloudDataSource.numberFactCalledCount)
            assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
            assertEquals(0, cacheDataSource.numberFactCalledList.size)
            assertEquals(0, cacheDataSource.saveNumberFactCalledCount)
        }
    }

    @Test
    fun test_random_number_fact_cached() = runBlocking {
        cloudDataSource.makeExpected(NumberData("10", "cloud 10"))
        cacheDataSource.replaceData(listOf(NumberData("10", "fact 10")))

        val expected = NumberFact("10", "cloud 10")
        val actual = repository.randomNumberFact()

        assertEquals(expected, actual)
        assertEquals(0, cloudDataSource.numberFactCalledCount)
        assertEquals(1, cloudDataSource.randomNumberFactCalledCount)
        assertEquals(0, cacheDataSource.numberFactCalledList.size)
        assertEquals(1, cacheDataSource.saveNumberFactCalledCount)
    }

    private class TestNumbersCloudDataSource : NumberCloudDataSource {

        private var thereIsConnection = true
        private var numberData = NumberData("", "")
        var numberFactCalledCount = 0
        var randomNumberFactCalledCount = 0

        fun changeConnection(connected: Boolean) {
            thereIsConnection = connected
        }

        fun makeExpected(data: NumberData) {
            numberData = data
        }

        override suspend fun number(number: String): NumberData {
            numberFactCalledCount++
            if (thereIsConnection) return numberData
            else throw UnknownHostException()
        }

        override suspend fun randomNumber(): NumberData {
            randomNumberFactCalledCount++
            if (thereIsConnection) return numberData
            else throw UnknownHostException()
        }

    }

    private class TestNumbersCacheDataSource : NumberCacheDataSource {

        var containsCalledList = mutableListOf<Boolean>()
        var numberFactCalledList = mutableListOf<String>()
        var allNumbersCalledCount = 0
        var saveNumberFactCalledCount = 0
        val data = mutableListOf<NumberData>()

        fun replaceData(newData: List<NumberData>) {
            data.clear()
            data.addAll(newData)
        }

        override suspend fun allNumbers(): List<NumberData> {
            allNumbersCalledCount++
            return data
        }

        override suspend fun contains(number: String): Boolean {
            val result = data.find { it.map(NumberData.Mapper.Matches(number)) } != null
            containsCalledList.add(result)
            return result
        }

        override suspend fun number(number: String): NumberData {
            numberFactCalledList.add(number)
            return data[0]
        }

        override suspend fun saveNumber(numberData: NumberData) {
            saveNumberFactCalledCount++
            data.add(numberData)
        }

    }

}