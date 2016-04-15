package section3

import java.io.InputStreamReader
import java.security.MessageDigest
import java.util.Date

import sun.misc.BASE64Encoder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/**
 * In this example, we want to synchronize three futures
 * in a more realistic scenario and show some traps.
 */
object Futures4 {

  def main(args: Array[String]) {

    /**
      * This method returns a future of a character stream
      */
    def loadResourceStream(resourceName: String): Future[Stream[Char]] = Future {
      Thread.sleep(1000)
      println("loadResourceStream: " + Thread.currentThread().getId + " : " + new Date)

      val in = new InputStreamReader(this.getClass.getResourceAsStream(resourceName))
      Stream.continually(in.read()).takeWhile(_ != -1).map(_.toChar)
    }

    /**
      * This method takes a character stream and returns a string future
      * which contains the sha1 calculated hash of the character stream.
      */
    def calculateSHA1(content: Stream[Char]): Future[String] = Future {
      Thread.sleep(2000)
      println("calculateSHA1: " + Thread.currentThread().getId + " : " + new Date)

      val hash = MessageDigest.getInstance("SHA1")
      content.foreach(c => hash.update(c.toByte))
      new BASE64Encoder().encode(hash.digest())
    }

    /**
      * This method takes a character stream and returns a boolean future
      * which contains the result of a virus check.
      */
    def checkForVirus(content: Stream[Char]): Future[Boolean] = Future {
      Thread.sleep(2000)
      println("checkForVirus: " + Thread.currentThread().getId + " : " + new Date)

      val definition = "virus"
      val findVirus = (mayVirus: String, elem: Char) => {
        if (definition.startsWith(mayVirus + elem)) {
          mayVirus + elem
        } else if(mayVirus == definition) {
          mayVirus
        } else {
          ""
        }
      }

      content.foldLeft[String]("")(findVirus) == definition
    }

    /**
      * As a result, we want to store out three future results in this container class.
      */
    case class ResourceInfo(content: String, hash: String, containsVirus: Boolean)

    /**
      * Let consider how our futures can be evaluated:
      * The hash and virus methods both needs the content stream. So the stream future must first be evaluated,
      * before we can hash and check. With all three values we can build the ResourceInfo.
      *
      *            +--> hash ---+
      * resource --+            +--> ResourceInfo(...)
      *            +--> check --+
      */

    /**
      * As the last example showed, we can put all our futures into a for-comprehension
      *
      * The stream future will be executed at first,
      * than the calculateSHA1 and the checkForVirus can take the stream
      * and start their futures calculations.
      *
      * At the end, we have all three values and put it into a ResourceInfo.
      * The result will be a Future[ResourceInfo]
      *
      * Hmm... But wait. In my test run I get:
      *  loadResourceStream: 10 : 16:00:32 CET 2014
      *  calculateSHA1: 11 :      16:00:34 CET 2014
      *  checkForVirus: 10 :      16:00:36 CET 2014
      *
      * Look at the seconds. Seems that the hash and check methods runs not in parallel, but sequential.
      * What happened? Because the for comprehension is only syntactic sugar for flatMap and map the calculations performs:

     loadResourceStream("test.txt").flatMap{stream =>
       calculateSHA1(stream).map { hash =>
         checkForVirus(stream).map { containsVirus =>
           ResourceInfo(stream.mkString, hash, containsVirus)
         }
       }
     }

     And there is our failure. Cause the cascaded mapping we perform our methods sequential and therefore perform the futures sequential.
      */
    val resourceFuture = for {
      stream <- loadResourceStream("test.txt")
      hash <- calculateSHA1(stream)
      containsVirus <- checkForVirus(stream)
    } yield {
      ResourceInfo(stream.mkString, hash, containsVirus)
    }

    /**
      * To let run the hash and check future in parallel we first need the stream:
      * Then we take the stream and create the two futures. They start immediately after the method call.
      *
      * These two futures are synchronized with a for comprehension.
      * As a result we get a Future[ResourceInfo], but know with much more performance.
      */
    val resourceFuture2 = loadResourceStream("test.txt").flatMap { stream =>
      val hashFuture: Future[String] = calculateSHA1(stream)
      val checkFuture: Future[Boolean] = checkForVirus(stream)

      for {
        hash <- hashFuture
        containsVirus <- checkFuture
      } yield {
        ResourceInfo(stream.mkString, hash, containsVirus)
      }
    }

    println(Await.result(resourceFuture, 8.seconds))
    println(Await.result(resourceFuture2, 8.seconds))
  }

}
