package com.soechka1.myfirstawesomeandroidproject.utils

import com.soechka1.myfirstawesomeandroidproject.model.CoroutineSettings
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

class CoroutineExecutor {


    // settings of hard work
    private companion object {
        const val MIN_DELAY_MS = 1_000L
        const val MAX_DELAY_MS = 10_000L
        const val ERROR_MS = 7_000L
        const val ERROR_PERCENT = 30
    }


    // hard link, but i clear memory after cancel and when program has done
    private var activeJobs = mutableListOf<Job>()

    // state for progress_bar in ui
    private var _totalCount = MutableStateFlow(0)
    val totalCount = _totalCount.asStateFlow()

    // state for exception handler in ui
    private var _errorType = MutableStateFlow<Int?>(null)
    val errorType = _errorType.asStateFlow()

    // status of our task
    private val _isRun = MutableStateFlow(false)
    val isRun = _isRun.asStateFlow()


    suspend fun startBatchOfCoroutines(
        coroutineScope: CoroutineScope,
        coroutineSettings: CoroutineSettings,
        continueWork: Boolean = false
    ) {
        _isRun.update { true }
        _errorType.update  { null }

        val handler = CoroutineExceptionHandler { _, exception ->
            when(exception) {
                is NetworkException -> _errorType.update { CoroutineError.ERROR_TOAST }
                is TokenExpiredException -> _errorType.update { CoroutineError.ERROR_SNACKBAR }
                is InvalidHashException -> _errorType.update { CoroutineError.ERROR_RESET_SETTINGS }
            }
        }

        // if we want to finished work after restart
        if(!continueWork){ _totalCount.update { 0 } }

        // clear memory
        activeJobs = mutableListOf()

        var startCoroutine: CoroutineStart = CoroutineStart.DEFAULT
        // but if we want start after start() or join()
        if (coroutineSettings.isLazyStart) {
            startCoroutine = CoroutineStart.LAZY
        }

        try {
            if (coroutineSettings.isParallel) {
                activeJobs = List(coroutineSettings.countOfCoroutine) { idx ->
                    coroutineScope.launch(
                        context = coroutineSettings.selectedDispatcher.toDispatcher() + SupervisorJob() + handler,
                        start = startCoroutine
                    ) {
                        executeCoroutine(idx)
                    }
                } as MutableList<Job>

                // start lazy coroutine, imitate lazy start
                if (coroutineSettings.isLazyStart) {
                    activeJobs.forEach { it.start() }
                }

                activeJobs.joinAll()

            } else {

                for(idx in 0 until coroutineSettings.countOfCoroutine) {
                    if (!_isRun.value) { break }

                    val job = coroutineScope.launch(
                        context = coroutineSettings.selectedDispatcher.toDispatcher() + SupervisorJob() + handler,
                        start = startCoroutine
                    ) { executeCoroutine(idx) }

                    activeJobs.add(job)
                    // start lazy coroutine, imitate lazy start
                    if (coroutineSettings.isLazyStart) {
                        job.start()
                    }

                    // if coroutines run sequential - we wait every job!
                    job.join()

                }
            }
        } finally {
            // all coroutines well done and we will update ui to change button
            _isRun.update { false }
            // clear memory
            activeJobs = mutableListOf()
        }
    }

    suspend fun continueWork(coroutineScope: CoroutineScope, coroutineSettings: CoroutineSettings){
        startBatchOfCoroutines(coroutineScope, coroutineSettings, true)
    }


    fun cancelAllCoroutines(): Int{
        val count = activeJobs.count { !it.isCompleted }
        activeJobs.forEach { it.cancel() }
        _isRun.update { false }
        // clear memory
        activeJobs = mutableListOf()
        return count
    }

    suspend fun executeCoroutine(index: Int) {
        val timeOfWork = Random.nextLong(MIN_DELAY_MS ..MAX_DELAY_MS )

        if(timeOfWork >= ERROR_MS  &&
            // 30% chance of happy message =)
            (Random.nextInt(100) < ERROR_PERCENT )){

            delay(timeOfWork)
            _totalCount.update { it + 1 }

            // imitate trouble with internet or token expired
            when(Random.nextInt(1..3)){
                1 -> { throw NetworkException(index.toString()) }
                2 -> { throw TokenExpiredException(index.toString()) }
                3 -> { throw InvalidHashException(index.toString()) }
            } // congratulations, congratulations!!!!!! coroutine finished without error
        } else { delay(timeOfWork) }
        _totalCount.update { it + 1 }
    }

}