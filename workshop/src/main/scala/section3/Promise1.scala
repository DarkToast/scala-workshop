package section3

import java.util.Date

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise}

/**
 * In the last examples we saw, that we can not synchronize an onSuccess handler
 * directly through the Future and the Await object. The handlers are executed concurrent,
 * triggered only by the result value of their future.
 *
 * For this problem, of an synchronization mechanism for concurrent callback functions, we have
 * the Promise type.
 */
object Promise1 {

  def main(args: Array[String]) {
    /**
      * Let us first define an example with an asynchronous service object.
      * This service has a method, which takes a value and a successHandler.
      *
      * The method starts a Future, which multiply the value with itself and register
      * the given successHandler on the future object.
      *
      * The service object does not expose the future object!
      *
      * Our problem is now: How can we synchronize this asynchronous call or just handle the resulting
      * success handler?
      */
    object SomeAsynchronousService {
      def makeSomeAsynchronous(value: Int, successHandler: PartialFunction[Int, Unit]) = {
        val f = Future {
          println("some example asynchronous request: " + Thread.currentThread().getId + " : " + new Date)
          Thread.sleep(2000)
          value * value
        }

        f.onSuccess(successHandler)
      }
    }

    /**
      * We now create a new Promise. A Promise is a container, which holds a promised value.
      * We give this promise to someone who can meet this value somewhere, sometime. This one
      * than pass the value to the promise and we can handle this event with the inline future.
      */
    val promise = Promise[Int]()

    /**
      * Let us now define our handler, which we will give to the SomeAsynchronousService.
      * The handler is a partial function which takes an Int (The result value) and just print
      * the result with some further information.
      *
      * And at last: We also use our promise, which we can access in this scope and fulfill the promise.
      * We put the result value with the success method to the promise. The promise also has a failure method,
      * which we can use for failure handling.
      */
    val handler: PartialFunction[Int, Unit] = {
      case result => {
        println(s"our handler with result '$result' : ${Thread.currentThread().getId} : ${new Date}")
        Thread.sleep(2000)
        promise success result
      }
    }

    /**
      * No we make the asynchronous call:
      */
    SomeAsynchronousService.makeSomeAsynchronous(10, handler)

    /**
      * To handle the fulfillment, the promise offers an internal future. With this future,
      * we now have an anchor to synchronize the calculated result (or use it at further handling)
      */
    val promisedFuture: Future[Int] = promise.future

    val calculatedResult: Int = Await.result(promisedFuture, 8.seconds)

    println(s"Finally the calculated result is: $calculatedResult")
  }

}
