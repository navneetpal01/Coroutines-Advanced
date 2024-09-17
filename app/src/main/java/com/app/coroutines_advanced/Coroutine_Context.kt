package com.app.coroutines_advanced

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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

fun main14() {

    val ctx: CoroutineContext = CoroutineName("A Name")

    val coroutineName: CoroutineName? = ctx[CoroutineName]
    // or ctx.get(CoroutineName)
    println(coroutineName?.name) // A name
    val job: Job? = ctx[Job] // or ctx.get(Job)
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

// Adding CoroutineContext

fun main15() {

    val ctx1: CoroutineContext = CoroutineName("Name1")
    println(ctx1[CoroutineName]?.name) // Name1
    println(ctx1[Job]?.isActive) // null

    val ctx2: CoroutineContext = Job()
    println(ctx2[CoroutineName]?.name) // null
    println(ctx2[Job]?.isActive) // true, because "Active"
    // is the default state of a job created this way

    val ctx3 = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name) // Name1
    println(ctx3[Job]?.isActive) // true

}

/**
 * The real power of CoroutineContext lies in its ability to merge contexts.
 * When combining contexts with different keys, the result responds to both keys.
 * If a new element with an existing key is added, it replaces the previous element,
 * similar to a map.
 *
 * The companion object here is named Key (instead of the default Companion),
 * but this doesn't change how it's used, only its reference name in reflection or extension functions.
 */

fun main16() {

    val ctx1: CoroutineContext = CoroutineName("Name1")
    println(ctx1[CoroutineName]?.name) // Name1

    val ctx2: CoroutineContext = CoroutineName("Name2")
    println(ctx2[CoroutineName]?.name) // Name2

    val ctx3 = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name) // Name2


}

// Empty CoroutineContext

fun main17() {

    val empty: CoroutineContext = EmptyCoroutineContext
    println(empty[CoroutineName]) // null
    println(empty[Job]) // null

    val ctxName = empty + CoroutineName("Name1") + empty
    println(ctxName[CoroutineName]) // CoroutineName(Name1)

}

/**
 * Subtracting elements
 * Elements can be removed from a context using minusKey.
 * The minus operator isn't overloaded for CoroutineContext to avoid ambiguity,
 * as explained in Effective Kotlin Item 12.
 */

fun main18() {

    val ctx = CoroutineName("Name1") + Job()
    println(ctx[CoroutineName]?.name) // Name1
    println(ctx[Job]?.isActive) // true

    val ctx2 = ctx.minusKey(CoroutineName)
    println(ctx[CoroutineName]?.name) // null
    println(ctx[Job]?.isActive) // true

    val ctx3 = (ctx + CoroutineName("Name2"))
        .minusKey(CoroutineName)
    println(ctx3[CoroutineName]?.name) // null
    println(ctx3[Job]?.isActive) // true

}

/**
 * Folding context
 * To perform an operation on each element within a context in Kotlin Coroutines, use the fold method. It requires:
 *     An initial accumulator value
 *     An operation to update the accumulator based on its current state and the current element
 */

fun main() {

    val ctx = CoroutineName("Name1") + Job()

    ctx.fold("") { acc, element -> "$acc$element" }
        .also(::println)
    // CoroutineName(Name1) JobImpl{Active}@dbab622e

    val empty = emptyList<CoroutineContext>()
    ctx.fold(empty) { acc, element -> acc + element }
        .joinToString()
        .also(::println)
    // CoroutineName(Name1) , JobImpl{Active}@dbab622e

}


































