package section3

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Synchronized {

  def main(args: Array[String]) {
    // If two threads access and modify a single and same used object,
    // we need to synchronize the access to prevent possible dead locks and
    // race conditions.
    var count: Int = 0

    // Every scala object has the synchronizes method, which takes a
    // method block. The lock is then the object of the synchronized method.
    // In this example out main object `Synchronizes`.
    def increment(): Unit = Synchronized.synchronized {
      println(s"Accessing increment from thread ${Thread.currentThread().getId}")

      val tmp = count + 1
      Thread.sleep(1000)
      count = tmp
    }

    // Two threads, which
    val f1 = Future(increment())
    val f2 = Future(increment())

    Await.ready(f1, Duration.Inf)
    Await.ready(f2, Duration.Inf)

    println(count)
  }

}
