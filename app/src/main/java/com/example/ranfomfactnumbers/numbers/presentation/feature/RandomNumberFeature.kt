package com.example.ranfomfactnumbers.numbers.presentation.feature

import com.example.ranfomfactnumbers.numbers.domain.RandomNumbersFactUseCase
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumbersCommunications
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumbersResultMapper

class RandomNumberFeature(
    private val useCase: RandomNumbersFactUseCase,
    private val communications: NumbersCommunications,
    private val numbersResultMapper: NumbersResultMapper,
) : NumbersFeature(communications, numbersResultMapper) {

    override suspend fun invoke() = useCase.factAboutRandomNumber()

}