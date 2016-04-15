package section3

import java.util.Date

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/**
 * Future synchronizations
 */
object Futures3 {

  def main(args: Array[String]) {
    // Let us define two futures
    val f1: Future[Int] = Future {
      Thread.sleep(2000)
      println("f1: " + Thread.currentThread().getId + " : " + new Date)
      20
    }

    val f2: Future[Int] = Future {
      Thread.sleep(1000)
      println("f2: " + Thread.currentThread().getId + " : " + new Date)
      10
    }

    // Now we want to synchronize these to futures and combine the two result to one result:

    // We can use the map and flatMap methods:
    // As a result, we get a third future, which holds out calculated result
    val f3: Future[Int] = f1.flatMap(i => f2.map(j => i+j))
    println(Await.result(f3, 2.seconds))

    // Optional we use the for comprehension instead of flatMap and map:
    val f4 = for {
      i <- f1
      j <- f2
    } yield { i + j }
    println(Await.result(f4, 2.seconds))
  }

}
