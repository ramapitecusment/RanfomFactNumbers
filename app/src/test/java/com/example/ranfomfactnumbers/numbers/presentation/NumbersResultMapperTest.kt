package com.example.ranfomfactnumbers.numbers.presentation

import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumberUiMapper
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumbersResultMapper
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumberUi
import com.example.ranfomfactnumbers.numbers.presentation.ui.UiState
import junit.framework.TestCase.assertEquals
import org.junit.Test

class NumbersResultMapperTest : BaseTest() {

    @Test
    fun test_error() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(NumberUiMapper(), communications)

        mapper.map("not empty message")

        assertEquals(UiState.Error("not empty message"), communications.stateCalledList[0])
    }

    @Test
    fun test_empty_list() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(NumberUiMapper(), communications)

        mapper.map(emptyList())

        assertEquals(0, communications.timesShowList)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)
    }

    @Test
    fun test_success_with_list() {
        val communications = TestNumbersCommunications()
        val mapper = NumbersResultMapper(NumberUiMapper(), communications)

        mapper.map(listOf(NumberFact("5", "fact 5")))

        assertEquals(1, communications.timesShowList)
        assertEquals(true, communications.stateCalledList[0] is UiState.Success)
        assertEquals(NumberUi("5", "fact 5"), communications.numbersList[0])
    }

}