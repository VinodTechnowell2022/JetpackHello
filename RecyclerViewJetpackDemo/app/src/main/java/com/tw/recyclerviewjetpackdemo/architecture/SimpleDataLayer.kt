package com.tw.recyclerviewjetpackdemo.architecture

import android.util.Log
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class AnswerService @Inject constructor() {

    suspend fun save(answer: String) {
        Log.v("Api call", "Make a blocking call to an api")
        delay(1000)
    }
}
