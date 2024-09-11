package com.app.coroutines_advanced

import android.provider.Settings.Global
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.math.truncate


suspend fun suspendingFun() {
    // .....

}

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

fun main1(){
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
    println("Hello,")
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

fun main(){
    thread(isDaemon = true){
        Thread.sleep(1000L)
        println("Mercury")
    }
    thread(isDaemon = true){
        Thread.sleep(1000L)
        println("Venus")
    }
    thread(isDaemon = true){
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
 * we might use runBlocking
 */











































