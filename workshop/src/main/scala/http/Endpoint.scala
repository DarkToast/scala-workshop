package http

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}

import scala.concurrent.Future

object MyServerMain extends MyServer

class MyServer extends HttpServer {

  override val defaultFinatraHttpPort: String = ":8080"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add(new MyController)
  }
}

class MyController extends Controller {

  import com.twitter.bijection.Conversion._
  import com.twitter.bijection.twitter_util.UtilBijections.twitter2ScalaFuture
  import com.twitter.util.{Future => TwitterFuture}

  import scala.concurrent.ExecutionContext.Implicits.global

  get("/foo") { request: Request =>
    val myF: Future[String] = Future {
      Thread.sleep(3000)
      "Hallo Welt"
    }

    myF.as[TwitterFuture[String]]
  }
}
