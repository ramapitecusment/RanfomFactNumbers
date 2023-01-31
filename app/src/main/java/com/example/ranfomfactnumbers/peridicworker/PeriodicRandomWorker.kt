package com.example.ranfomfactnumbers.peridicworker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ranfomfactnumbers.numbers.data.NumbersRepository
import com.example.ranfomfactnumbers.numbers.data.RandomNumberRepository
import org.koin.java.KoinJavaComponent
import java.lang.Exception

class PeriodicRandomWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val koin = KoinJavaComponent.getKoin()

    private val repository: RandomNumberRepository by koin.inject()

    override suspend fun doWork() = try {
        Log.d("PeriodicRandomWorker", "doWork")
        repository.randomNumberFact()
        Result.success()
    } catch (e: Exception) {
        Log.e("PeriodicRandomWorker", "Exception: $e")
        Result.retry()
    }

}