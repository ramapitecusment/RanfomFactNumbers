package com.example.ranfomfactnumbers.numbers.presentation.feature

import com.example.ranfomfactnumbers.numbers.domain.NumbersInitialUseCase
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumbersCommunications
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumbersResultMapper

class NumbersInitialFeature(
    private val useCase: NumbersInitialUseCase,
    private val communications: NumbersCommunications,
    private val numbersResultMapper: NumbersResultMapper,
) : NumbersFeature(communications, numbersResultMapper) {

    override suspend fun invoke() = useCase.init()

}