package com.example.ranfomfactnumbers.numbers.data.cloud

import com.example.ranfomfactnumbers.numbers.data.NumberData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NumbersCloudDataSourceTest {

    @Test
    fun `test number`() = runBlocking {
        val apiHeader = RandomApiHeader.Mock("test")
        val service = MockNumbersService(apiHeader)
        val dataSource = NumberCloudDataSource.Base(service, apiHeader)

        val actual = dataSource.number("1")
        val expected = NumberData("1", "fact about 1")
        assertEquals(expected, actual)
    }

    @Test
    fun `test random success`() = runBlocking {
        val apiHeader = RandomApiHeader.Mock("test")
        val service = MockNumbersService(apiHeader)
        val dataSource = NumberCloudDataSource.Base(service, apiHeader)

        val actual = dataSource.randomNumber()
        val expected = NumberData("1", "fact about 1")
        assertEquals(expected, actual)
    }

    @Test(expected = IllegalStateException::class)
    fun `test random failed`(): Unit = runBlocking {
        val apiHeader = RandomApiHeader.Mock("test")
        val service = MockNumbersService(apiHeader)
        val emptyHeader = RandomApiHeader.Mock("")
        val dataSource = NumberCloudDataSource.Base(service, emptyHeader)

        dataSource.randomNumber()
    }
}