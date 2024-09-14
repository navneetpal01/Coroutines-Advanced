package com.app.coroutines_advanced

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
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
 * why we need to use the GlobalScope ? It is because launch and async are the extension functions but runBlocking isn't
 * on CoroutineScope In runBlocking we get the block parameter in a function type whose receiver type is
 * CoroutineScope.
 * runBlocking blocks the thread on which it is run for example Main thread
 */

/**
 * This means that we can get rid of GlobalScope, instead launch can be called on the receiver provided
 * by the runBlocking, so with this.launch or simply launch. as a result launch becomes the child of runBlocking
 * As parents might recognize, a parental responsibility to wait for all of it's children are finished
 */

fun main11() {
    runBlocking {
        this.launch {//same as just launch
            delay(1000L)
            println("Mercury")
        }
        launch {
            delay(500L)
            println("Earth")
        }
        println("Hello, ")

    }

    /**
     * Hello,
     * Earth
     * Mercury
     */
}

/**
 * A parent coroutine provides a scope for its children,
 * creating a structured concurrency relationship. This has key effects:
 * Context Inheritance:
 *
 *     Children inherit context from their parent, though they can override it.
 *     Parent Suspension: A parent waits until all children finish.
 *     Cancellation Propagation: Canceling a parent cancels its children.
 *     Error Propagation: A child's error destroys the parent.
 *
 * Unlike other coroutine builders,
 * runBlocking cannot be a child. It's always the root, starting the entire hierarchy. This differentiates it from other builders.
 */

/**
 * Suspending functions must be called from other suspending functions.
 *
 * Coroutine builders (except runBlocking) need a CoroutineScope. Builders can start other builders on their scope. This is how applications are structured.
 *
 * MainPresenter is common for Android. UserController is common for backend.
 */

//Using Coroutine scope

/**
 * When working with suspending functions, you need a coroutine scope to manage asynchronous tasks,
 * but passing the scope as an argument isn’t ideal. Instead, use the coroutineScope function.
 * It creates a coroutine scope within its lambda and returns the result of that lambda. For instance,
 * if you're loading user data and articles asynchronously, coroutineScope helps manage these tasks without manually
 * handling the scope. This function is essential for handling asynchronous code cleanly and will be explored in
 * detail in the chapter on coroutine scope functions.
 */

/**
 * suspend fun getArticlesForUser(
 * userToken: String?,
 * ): List<ArticleJson> = coroutineScope {
 * val articles = async { articleRepository.getArticles() }
 * val user = userService.getUser(userToken)
 * articles.await()
 * .filter { canSeeOnList(user, it) }
 * .map { toArticleJson(it) }
 * }
 */

suspend fun main12() = coroutineScope{
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello, ")
}

/**
 *
 * Inherits the dispatcher from the parent scope
 * don't waits for it's children to finish
 */



/**
 * Kotlin coroutines are a powerful tool for asynchronous programming.
 * The basic concepts involve suspending functions, coroutine scopes, and builders.
 * While this knowledge is sufficient for most uses, there's much more to explore,
 * including context management, cancellation handling, exception handling, and testing.
 */








