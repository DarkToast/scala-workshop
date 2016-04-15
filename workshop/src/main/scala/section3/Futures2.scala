package section3

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Random, Success}

object Futures2 {

  def main(args: Array[String]) {
    /**
      * A future block is also a closure, so we can use values from the outer scope in it.
      */
    def divide(x: Int, y: Int): Future[Double] = Future {
      Thread.sleep(Random.nextInt(2000))
      println("Calculating result")
      x/y
    }

    // Our divide method returns not a Double value, but a future of Double.
    val f: Future[Double] = divide(4, 2)

    // If we do not want to block our thread and make a hard wait,
    // we can add callback methods to the future. Callbacks?!
    // Hail yeah! We are buzzword conform and reactive man!

    // Either a single onSuccess handler with a partial function:
    f.onSuccess {
      case result: Double => println(s"The computed division onSuccess is: $result")
    }

    // Or a single onFailure handler with a partial function on a Throwable input value.
    f.onFailure {
      case error: Throwable => println(s"An error occurred onFailure: '${error.getMessage}'")
    }

    // Or a central handler, which takes a Try object, which can be either a Success
    // or a Failure.
    f.onComplete {
      case Success(result) => println(s"The computed division onComplete is: $result")
      case Failure(error) => println(s"An error occurred onFailure: '${error.getMessage}'")
    }

    // Hint: An await.result awaits the result, but NOT the callback!
    // To await the callback, we can use Promises (later!)
    val value = Await.result(f, 4.seconds)
    println(s"We have a result! $value")
    Thread.sleep(500)
  }
}
