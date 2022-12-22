package com.example.ranfomfactnumbers.numbers.presentation

import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import com.example.ranfomfactnumbers.numbers.domain.NumbersInteractor
import com.example.ranfomfactnumbers.numbers.domain.NumbersResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class NumbersViewModelTest : BaseTest() {

    private lateinit var mapper: NumbersResultMapper
    private lateinit var viewModel: NumbersViewModel
    private lateinit var dispatchers: DispatchersList
    private lateinit var interactor: TestNumbersInteractor
    private lateinit var manageResources: TestManageResources
    private lateinit var communications: TestNumbersCommunications

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun init() {
        Dispatchers.setMain(mainThreadSurrogate)

        dispatchers = TestDispatchers()
        interactor = TestNumbersInteractor()
        manageResources = TestManageResources()
        communications = TestNumbersCommunications()

        mapper = NumbersResultMapper(NumberUiMapper(), communications)
        viewModel = NumbersViewModel(dispatchers, interactor, manageResources, mapper, communications)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
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
        assertEquals(true, communications.progressCalledList[0])
        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(0, communications.timesShowList)

        // get some data
        interactor.changeExpectedResult(NumbersResult.Failure("No internet connection"))
        viewModel.fetchRandomNumberFact()

        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

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
        interactor.changeExpectedResult(NumbersResult.Success(listOf(NumberFact("45", "random fact about 45"))))
        viewModel.fetchNumberFact("45")

        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)
        assertEquals(NumberFact("45", "fact about 45"), interactor.fetchAboutNumberCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success, communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
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