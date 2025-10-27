package softserve.academy.myapplication.demo

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

val startTime = System.currentTimeMillis()

suspend fun main() = coroutineScope {
    val deferred: Deferred<String> = async { sayHello() }
    println("Middle")
    println("It says ${deferred.await()}")
}

suspend fun sayHello(): String  {
    delay(1000L)
    return "Hello!"
}

suspend fun main10() = coroutineScope {
    val job = launch(start = CoroutineStart.LAZY) {
        delay(200L)
        println("Coroutine has started")
    }

    delay(1000L)
    println("Main middle")
    job.start()
    println("Main end")
}

suspend fun main9() = coroutineScope {
    launch {
        delay(200L)
        println("Coroutine has started")
    }

    delay(1000L)
    println("Main end")
}

fun main8() = runBlocking {
    launch {
        (1..5).forEach {
            delay(400L)
            println(it)
        }
    }
    launch {
        (6..10).forEach {
            delay(400L)
            println(it)
        }
    }
    println("End of main")
}

suspend fun main7() = coroutineScope {
    launch {
        (1..5).forEach {
            delay(400L)
            println(it)
        }
    }
    launch {
        (6..10).forEach {
            delay(400L)
            println(it)
        }
    }
    println("End of main")
}

suspend fun main6() {
    doWork1()
    println("End of main")
}

suspend fun doWork1() = coroutineScope {
    launch {
        repeat(5) {
            delay(400L)
            println(it)
        }
    }
}

suspend fun main5() = coroutineScope<Unit> {
    launch { doWork() }
//    println("End of main")
}

suspend fun doWork() {
    repeat(5) {
        delay(400L)
        println(it)
    }
}

suspend fun main4() = coroutineScope {
    launch {
        repeat(5) {
            delay(400L)
            println(it)
        }
    }
    println("End of main")
}

suspend fun main3() {
    repeat(5) {
        delay(400L)
        println(it)
    }
    println("End of main")
}

fun main2() = runBlocking {
    printThreadAndTime()
    println("Start main")
    joinAll(
        async { fooCoroutine("foo1", 500) },
        async { fooCoroutine("foo2", 300) }
    )
    printThreadAndTime()
    println("End main")
}

fun main1() {
    printThreadAndTime()
    println("Start main")
    val t1 = thread {
        foo("foo1", 500)
    }
    val t2 = thread {
        foo("foo2", 300)
    }
    t1.join()
    t2.join()
    printThreadAndTime()
    println("End main")
}

fun printThreadAndTime() {
    print("Thread ${Thread.currentThread().name} T(${System.currentTimeMillis() - startTime}) > ")
}

fun foo(name: String, pauseMillis: Long) {
    printThreadAndTime()
    println("Start $name")
    Thread.sleep(pauseMillis)
    printThreadAndTime()
    println("End $name")
}

suspend fun fooCoroutine(name: String, pauseMillis: Long) {
    printThreadAndTime()
    println("Start $name")
    delay(pauseMillis)
    withContext(Dispatchers.IO) {
        printThreadAndTime()
        println("End $name")
    }
}