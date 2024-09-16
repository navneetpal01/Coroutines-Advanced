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

fun main13() {

    val name: CoroutineName = CoroutineName("A name")
    val element1: CoroutineContext.Element = name
    val element2: CoroutineContext.Element = name
    val context: CoroutineContext = element1 + element2

    val job: Job = Job()
    val jobElement: CoroutineContext.Element = job
    val jobContext: CoroutineContext = jobElement

    // This shit is crazy
    GlobalScope.launch(context + jobContext) {

    }

}

/**
 * SupervisorJob, CoroutineExceptionHandler, and dispatchers from Dispatchers
 * are essential coroutine contexts. They will be discussed in detail later.
 */

/**
 * CoroutineContext is like a collection. Use get() or square brackets
 * to find elements by key. If the element exists, it's returned; otherwise,
 * null.
 */

fun main14(){

    val ctx : CoroutineContext = CoroutineName("A Name")

    val coroutineName : CoroutineName? = ctx[CoroutineName]
    // or ctx.get(CoroutineName)
    println(coroutineName?.name) // A name
    val job : Job? = ctx[Job] // or ctx.get(Job)
    println(job) // null

    /**
     * A Name
     * null
     */

}

/**
 * In Kotlin, CoroutineContext is built-in, while Job and CoroutineName are from kotlinx.coroutines
 * To access CoroutineName, use CoroutineName directly. It's a companion object, not a class.
 */

/**
 * In kotlinx.coroutines, companion objects are often used as keys
 * This makes it easier to remember key-value pairs. For example, CoroutineName
 * is a key, and Job is an interface with multiple implementations.
 */

interface Job : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Job>
}



