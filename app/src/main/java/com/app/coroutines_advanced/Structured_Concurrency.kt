package com.app.coroutines_advanced

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * If a coroutine is started on a GlobalScope, the program will not wait for it
 * coroutines do not block any thread nothing prevents the function from ending
 * that's why an additional delay is used at the end of the runBlocking if we want to see "World".
 */

fun main10() {
    runBlocking {
        GlobalScope.launch {
            delay(1000)
            println("Mercury")
        }
        GlobalScope.launch {
            delay(2000)
            println("Venus")
        }
        println("Hello, ")
        // delay(3000)
    }
}

/**
 * why we need to use the GlobalScope ? It is because launch and async are the extension functions
 * on CoroutineScope In runBlocking we get the block parameter in a function type whose receiver type is
 * CoroutineScope.
 */

/**
 * This means that we can get rid of GlobalScope, instead launch can be called on the receiver provided
 * by the runBlocking, so with this.launch or simply launch. as a result launch becomes the child of runBlocking
 * As parents might recognize, a parental responsibility to wait for all of it's children are finished
 */

fun main() = runBlocking {
    this.launch {//same as just launch
        delay(1000L)

    }
}









