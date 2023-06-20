package com.example.ranfomfactnumbers.numbers.presentation

import android.view.View
import com.example.ranfomfactnumbers.main.presentation.DispatchersList
import com.example.ranfomfactnumbers.main.presentation.ManageResources
import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import com.example.ranfomfactnumbers.numbers.domain.NumbersInteractor
import com.example.ranfomfactnumbers.numbers.domain.NumbersResult
import com.example.ranfomfactnumbers.numbers.presentation.feature.NumbersFactFeature
import com.example.ranfomfactnumbers.numbers.presentation.feature.NumbersInitialFeature
import com.example.ranfomfactnumbers.numbers.presentation.feature.RandomNumberFeature
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumberUiMapper
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumbersResultMapper
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumberUi
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumbersViewModel
import com.example.ranfomfactnumbers.numbers.presentation.ui.UiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test

class NumbersViewModelTest : BaseTest() {

    private lateinit var mapper: NumbersResultMapper
    private lateinit var viewModel: NumbersViewModel
    private lateinit var dispatchers: DispatchersList
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var manageResources: TestManageResources
    private lateinit var communications: TestNumbersCommunications
    private lateinit var initial: NumbersInitialFeature
    private lateinit var randomNumberFact: RandomNumberFeature
    private lateinit var numberFact: NumbersFactFeature

    @Before
    fun init() {
        dispatchers = TestDispatchers()
        interactor = TestNumbersInteractor()
        manageResources = TestManageResources()
        communications = TestNumbersCommunications()
        mapper = NumbersResultMapper(NumberUiMapper(), communications)

        initial = NumbersInitialFeature(interactor, communications, mapper)
        randomNumberFact = RandomNumberFeature(interactor, communications, mapper)
        numberFact = NumbersFactFeature.Base(interactor, manageResources, communications, mapper)

        viewModel = NumbersViewModel(dispatchers, initial, randomNumberFact, numberFact, communications)
    }

    /**
     * Initial test
     * At start fetch data and show it
     * Then try to get some data successfully
     * Then re-init and check the result
     */
    @Test
    fun `test init and re-init`() = runBlocking {
        interactor.changeExpectedResult(NumbersResult.Success())
        // 2. action
        viewModel.init(isFirstRun = true)
        // 3. check
        assertEquals(View.VISIBLE, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)

        // get some data
        interactor.changeExpectedResult(NumbersResult.Failure("No internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(View.VISIBLE, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("No internet connection"), communications.stateCalledList[1])
        assertEquals(0, communications.timesShowList)

        // rotate screen
        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(0, communications.timesShowList)

    }

    /**
     * Try to get information about empty number
     */
    @Test
    fun `fact about empty number`() = runBlocking {
        manageResources.string = "Entered number is empty"
        viewModel.fetchNumberFact("")

        assertEquals(0, interactor.fetchAboutNumberCalledList.size)

        assertEquals(0, communications.progressCalledList.size)

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("Entered number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.timesShowList)
    }

    /**
     * Try to get information about some number
     */
    @Test
    fun `fact about some number`() = runBlocking {
        interactor.changeExpectedResult(NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))))
        viewModel.fetchNumberFact("45")

        assertEquals(View.VISIBLE, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
        assertEquals(NumbersResult.Success(listOf(NumberFact("45", "fact about 45"))), interactor.fetchAboutNumberCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(View.GONE, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
    }

    @Test
    fun test_clear_error() {
        viewModel.clearError()
        assertEquals(1, communications.stateCalledList.size)
        assertEquals(true, communications.stateCalledList[0] is UiState.ClearError)
    }

    private class TestManageResources : ManageResources {

        var string = ""

        override fun string(id: Int): String {
            return string
        }

    }

    private class TestNumbersInteractor : NumbersInteractor {

        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }

    }

    private class TestDispatchers : DispatchersList {

        override fun io(): CoroutineDispatcher = TestCoroutineDispatcher()

        override fun ui(): CoroutineDispatcher = TestCoroutineDispatcher()

    }

}