package com.app.coroutines_advanced

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * If you look at coroutine builder there first parameter is of type CoroutineContext
 */

/**
 * CoroutineContext
 * is an interface that represents a collection of elements. Each element is itself a CoroutineContext,
 * making it a nested collection. Think of it like a mug that can hold other mugs, creating a nested structure.
 * This allows for easy context specification and modification.
 */

fun main(){

    val name : CoroutineName = CoroutineName("A name")
    val element1 : CoroutineContext.Element = name
    val element2 : CoroutineContext.Element = name
    val context : CoroutineContext = element1 + element2

    val job : Job = Job()
    val jobElement : CoroutineContext.Element = job
    val jobContext : CoroutineContext = jobElement

    GlobalScope.launch(CoroutineName("A") + Job()){

    }
}

/**
 * SupervisorJob, CoroutineExceptionHandler, and dispatchers from Dispatchers
 * are essential coroutine contexts. They will be discussed in detail later.
 */



