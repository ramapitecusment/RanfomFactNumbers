package com.example.ranfomfactnumbers.numbers.presentation.feature

import android.view.View
import com.example.ranfomfactnumbers.main.presentation.Handle
import com.example.ranfomfactnumbers.main.presentation.UiFeature
import com.example.ranfomfactnumbers.numbers.domain.NumbersResult
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumbersCommunications
import com.example.ranfomfactnumbers.numbers.presentation.mapper.NumbersResultMapper
import kotlinx.coroutines.Job

abstract class NumbersFeature(
    private val communications: NumbersCommunications,
    private val numbersResultMapper: NumbersResultMapper,
) : UiFeature, suspend () -> NumbersResult {

    override fun handle(handle: Handle): Job {
        communications.showProgress(View.VISIBLE)
        return handle.handle(this) { result ->
            communications.showProgress(View.GONE)
            result.map(numbersResultMapper)
        }
    }

}