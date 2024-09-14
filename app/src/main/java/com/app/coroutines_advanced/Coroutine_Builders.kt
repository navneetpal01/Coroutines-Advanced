@file:JvmName("NewName") //Name of the class
package com.app.coroutines_advanced

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

//Changing the name of our main function class from default name
//fun main(){
//    println("Hello, World")
//}
//
//suspend fun suspendingFun() {
//    // .....
//
//}

fun normalFun() {
    // .....
//    suspendingFun() // Error: Suspending function needs to be called by another suspending function
}

/**
 * Three essentials coroutine builders provided by the kotlinx.coroutines
 * launch
 * runBlocking
 * async
 */

/**
 * Launch Builder -
 * The way launch works in conceptually similar to starting a new thread
 * we just start a coroutine and it will run independently, like a firework
 * that is launched into the air
 */

fun main1() {
    //Global scope is not tied to the lifecycle such as: Activities Fragments and ViewModels
    GlobalScope.launch {
        delay(1000L)
        println("Mercury")
        println("Current thread: ${Thread.currentThread()}")
    }
    GlobalScope.launch {
        delay(1000L)
        println("Venus")
        println("Current thread: ${Thread.currentThread()}")
    }
    GlobalScope.launch {
        delay(500L)
        println("Earth")
        println("Current thread: ${Thread.currentThread()}")
    }
    println("Hello,")
    println("Current thread: ${Thread.currentThread()}")
    Thread.sleep(2000L)

}

/**
 * Launch function is an extension function on the CoroutineScope interface
 * part of a structured mechanism called structured concurrency
 * to build a relationship between the parent coroutine and the child
 *
 * Thread.sleep(2000L) keeps the function running else the function wille end immediately
 * after launching the coroutines so they wouldn't have a chance to do their job
 * and this is because the delay does not block the thread it just suspends the coroutine
 *
 * Latter we will use the structured concurrency, Thread.sleep is not needed
 */


/**
 * To some degree, how launch works is similar to the demon thread but much cheaper
 * demon is useful initially but becomes problematic latter Maintaining a blocked thread
 * is always costly, while maintaining a suspended coroutine is almost free
 * They both start some independent processes and need something that will prevent
 * the program ending before they are done and in this case it is Thread.sleep(2000L)
 */

fun main2() {
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        println("Mercury")
    }
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        println("Venus")
    }
    thread(isDaemon = true) {
        Thread.sleep(500L)
        println("Earth")
    }
    println("Hello, ")
    Thread.sleep(2000L)
}

/**
 * General rule of the coroutine should never block threads, only suspend them
 * Both daemon and launch do not stops the program from ending like in the function
 * earlier we blocked the thread otherwise our program will end too early For such cases
 * we might use runBlocking, It blocks the thread it has been started on whenever it's coroutine
 * is suspended.
 *
 * This means that the delay inside runBlocking will behave like Thread.sleep(1000L)
 */

fun main3() {

    runBlocking {
        delay(1000)
        println("Mercury")
    }

    runBlocking {
        delay(1000)
        println("Venus")
    }

    runBlocking {
        delay(500)
        println("Earth")
    }
    println("Hello, ")

}

/**
 * It runs a new coroutine and blocks the current thread interruptibly until it's completion
 * using a dispatcher we can make run on a different thread, still the thread on which this builder
 * has been started will be blocked until the coroutine will be done
 */

fun main4() {

    Thread.sleep(1000L)
    println("Mercury")
    Thread.sleep(1000L)
    println("Venus")
    Thread.sleep(500L)
    println("Earth")
    print("Hello, ")

}

fun main5() = runBlocking {

    GlobalScope.launch {
        delay(1000L)
        println("Mercury")
    }
    GlobalScope.launch {
        delay(1000L)
        println("Venus")
    }
    GlobalScope.launch {
        delay(500L)
        println("Earth")
    }

    println("Hello, ")
    delay(2000L) // Works as Thread.sleep(2000L)

}

suspend fun main6() {

    GlobalScope.launch {
        delay(1000L)
        println("Mercury")
    }
    GlobalScope.launch {
        delay(1000L)
        println("Venus")
    }
    GlobalScope.launch {
        delay(1000L)
        println("Earth")
    }
    println("Hello, ")
    delay(2000L)

    /**
     * Hello,
     * Mercury
     * Venus
     * Earth
     */


}

/**
 * Async Builder
 * Similar to launch, but is designed to produce a value, The value needs to be returned
 * by the lambda ex- pression. The async function returns an object of type Deferred<T>
 * where T is the type of the produced value. Deferred has a suspending method await,
 * which returns once it is ready.
 *
 * In the example below, the produced value is 42, and it's type Int
 */

fun main7() = runBlocking {
    val resultDeferred: Deferred<Int> = GlobalScope.async {
        delay(1000L)
        42
    }

    // do other stuff.....
    val result: Int = resultDeferred.await()
    println(result) // 42
    // or just

}

/**
 * used runBlocking here so that we can suspend the coroutine until the value is received
 * if we use the launch builder the function will end without waiting for the value to be returned
 * so it won't print the value
 */

fun main8() = runBlocking {

    val res1 = GlobalScope.async{
        delay(1000L)
        "Text 1"
    }

    val res2 = GlobalScope.async{
        delay(3000L)
        "Text 2"
    }

    val res3 = GlobalScope.async {
        delay(3000)
        "Text 3"
    }

    println(res1.await())
    println(res2.await())
    println(res3.await())

    /**
     * Text 1
     * Text 2
     * Text 3
     */

}

/**
 * async builder is similar to launch, but it has support for returning a value
 * but async is all about producing the values
 */

fun main9() = runBlocking{
    // Don't do this
    // This is misleading to use async as launch
    GlobalScope.async {
        delay(1000L)
        println("World")
    }
    println("Hello")
    delay(2000L)
}





































