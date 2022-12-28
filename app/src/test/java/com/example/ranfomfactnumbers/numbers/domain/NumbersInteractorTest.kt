package com.example.ranfomfactnumbers.numbers.domain

import com.example.ranfomfactnumbers.numbers.data.NumbersRepository
import com.example.ranfomfactnumbers.numbers.presentation.ManageResources
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NumbersInteractorTest {

    private lateinit var handleError: HandleError
    private lateinit var interactor: NumbersInteractor
    private lateinit var handleRequest: HandleRequest
    private lateinit var manageResources: TestManageReSources
    private lateinit var repository: TestNumbersRepository

    @Before
    fun setup() {
        repository = TestNumbersRepository()
        manageResources = TestManageReSources()
        handleError = HandleError.Base(manageResources)
        handleRequest = HandleRequest.Base(handleError, repository)
        interactor = NumbersInteractor.Base(handleRequest, repository)
    }

    @Test
    fun test_init_success() = runBlocking {
        repository.changeExpectedList(listOf(NumberFact("6", "fact about 6")))

        val actual = interactor.init()
        val expected = NumbersResult.Success(listOf(NumberFact("6", "fact about 6")))

        assertEquals(expected, actual)
        assertEquals(1, repository.allNumbersCalledCount)
    }

    @Test
    fun test_fact_about_number_success() = runBlocking {
        repository.changeExpectedFactOfNumber(NumberFact("7", "expected 7"))

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Success(listOf(NumberFact("7", "expected 7")))

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun test_fact_about_number_error() = runBlocking {
        repository.expectingErrorGetFact(true)
        manageResources.changeExpectedString("no internet connection")

        val actual = interactor.factAboutNumber("7")
        val expected = NumbersResult.Failure("no internet connection")

        assertEquals(expected, actual)
        assertEquals("7", repository.numberFactCalledList[0])
        assertEquals(1, repository.numberFactCalledList.size)
    }

    @Test
    fun test_fact_about_random_number_success() = runBlocking {
        repository.changeExpectedFactOfRandomNumber(NumberFact("7", "expected 7"))

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Success(listOf(NumberFact("7", "expected 7")))

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)
    }

    @Test
    fun test_fact_about_random_number_error() = runBlocking {
        repository.expectingErrorGetRandomFact(true)
        manageResources.changeExpectedString("no internet connection")

        val actual = interactor.factAboutRandomNumber()
        val expected = NumbersResult.Failure("no internet connection")

        assertEquals(expected, actual)
        assertEquals(1, repository.randomNumberFactCalledList.size)
    }

    private class TestNumbersRepository : NumbersRepository {

        private val allNumbers = mutableListOf<NumberFact>()
        private var numberFact = NumberFact("", "")
        private var errorWhileNumberFact = false

        var allNumbersCalledCount = 0
        val numberFactCalledList = mutableListOf<String>()
        val randomNumberFactCalledList = mutableListOf<String>()

        fun changeExpectedList(list: List<NumberFact>) {
            allNumbers.clear()
            allNumbers.addAll(list)
        }

        fun changeExpectedFactOfNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun changeExpectedFactOfRandomNumber(numberFact: NumberFact) {
            this.numberFact = numberFact
        }

        fun expectingErrorGetFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        fun expectingErrorGetRandomFact(error: Boolean) {
            errorWhileNumberFact = error
        }

        override suspend fun allNumbers(): List<NumberFact> {
            allNumbersCalledCount++
            return allNumbers
        }

        override suspend fun numberFact(number: String): NumberFact {
            numberFactCalledList.add(number)
            if (errorWhileNumberFact) throw NoInternetConnectionException()
            allNumbers.add(numberFact)
            return numberFact
        }

        override suspend fun randomNumberFact(): NumberFact {
            randomNumberFactCalledList.add("7")
            if (errorWhileNumberFact) throw NoInternetConnectionException()
            allNumbers.add(numberFact)
            return numberFact
        }

    }

    private class TestManageReSources : ManageResources {

        private var value = ""

        fun changeExpectedString(string: String) {
            value = string
        }

        override fun string(id: Int) = value

    }

}