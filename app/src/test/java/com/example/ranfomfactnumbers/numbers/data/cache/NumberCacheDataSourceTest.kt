package com.example.ranfomfactnumbers.numbers.data.cache

import com.example.ranfomfactnumbers.numbers.data.NumberData
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NumberCacheDataSourceTest {

    private lateinit var dao: TestDao
    private lateinit var dataSource: NumberCacheDataSource

    @Before
    fun setup() {
        dao = TestDao()
        dataSource = NumberCacheDataSource.Base(dao, TestMapper(5))
    }

    @Test
    fun test_all_numbers_empty() = runBlocking {
        val actual = dataSource.allNumbers()

        assertEquals(true, actual.isEmpty())
    }

    @Test
    fun test_all_numbers_not_empty() = runBlocking {
        dao.data.add(NumberCache("1", "fact 1", 1))
        dao.data.add(NumberCache("2", "fact 2", 2))

        val actualList = dataSource.allNumbers()
        val expected = listOf(NumberData("1", "fact 1"), NumberData("2", "fact 2"))

        assertEquals(actualList, expected)
    }

    @Test
    fun test_contains() = runBlocking {
        dao.data.add(NumberCache("6", "fact 6", 6))

        val actual = dataSource.contains("6")
        val expected = true

        assertEquals(expected, actual)
    }

    @Test
    fun test_not_contains() = runBlocking {
        val actual = dataSource.contains("6")
        val expected = false

        assertEquals(expected, actual)
    }

    @Test
    fun test_save_number() = runBlocking {
        dataSource.saveNumber(NumberData("8", "fact 8"))

        assertEquals(NumberCache("8", "fact 8", 5), dao.data[0])
    }

    @Test
    fun test_number_contains() = runBlocking {
        dao.data.add(NumberCache("10", "fact 10", 10))

        val actual = dataSource.number("10")
        val expected = NumberData("10", "fact 10")

        assertEquals(expected, actual)
    }

    @Test
    fun test_number_not_contains() = runBlocking {
        dao.data.add(NumberCache("10", "fact 10", 10))

        val actual = dataSource.number("32")
        val expected = NumberData("", "")

        assertEquals(expected, actual)
    }
}

private class TestDao : NumbersDao {
    val data = mutableListOf<NumberCache>()

    override fun allNumbers() = data

    override fun insert(map: NumberCache) {
        data.add(map)
    }

    override fun number(number: String) = data.find { it.number == number }

}

private class TestMapper(private val date: Long) : NumberData.Mapper<NumberCache> {

    override fun map(id: String, fact: String) = NumberCache(id, fact, date)

}