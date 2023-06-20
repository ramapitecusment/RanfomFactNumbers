package com.example.ranfomfactnumbers.numbers.presentation.feature

import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.main.presentation.Handle
import com.example.ranfomfactnumbers.main.presentation.ManageResources
import com.example.ranfomfactnumbers.main.presentation.UiFeature
import com.example.ranfomfactnumbers.numbers.domain.NumbersFactUseCase
import com.example.ranfomfactnumbers.numbers.domain.NumbersResult
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumbersCommunications
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumbersResultMapper
import kotlinx.coroutines.Job

interface NumbersFactFeature {

    fun number(number: String): UiFeature

    class Base(
        private val useCase: NumbersFactUseCase,
        private val manageResources: ManageResources,
        private val communications: NumbersCommunications,
        private val numbersResultMapper: NumbersResultMapper,
    ) : NumbersFeature(communications, numbersResultMapper), NumbersFactFeature {

        private var number: String = ""

        override fun number(number: String): UiFeature {
            this.number = number
            return this
        }

        override fun handle(handle: Handle) = if (number.isEmpty()) handleEmptyNumberError(handle)
        else super.handle(handle)

        override suspend fun invoke() = useCase.factAboutNumber(number)

        private fun handleEmptyNumberError(handle: Handle) = handle.handle(
            { NumbersResult.Failure(manageResources.string(R.string.empty_number_error_message)) },
            { it.map(numbersResultMapper) }
        )

    }

}