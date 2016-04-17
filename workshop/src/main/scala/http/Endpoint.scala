package http

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}
import http.di.{Environment, RuntimeEnvironment}
import http.shop.LineItem
import net.liftweb.json.{Serialization, _}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ServerMain extends Server

class Server extends HttpServer {
  override val defaultFinatraHttpPort: String = ":8080"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add(new ShopController with RuntimeEnvironment)
  }
}

class ShopController extends Controller {
  this: Environment =>

  implicit val formats = DefaultFormats

  get("/products") { request: Request =>
    val search = request.params.getOrElse("search", "")
    val resultF = Await.result(shop.search(search), Duration.Inf)
    response.ok.json(resultF)
  }

  post("/orders") { request: Request =>
    val items = Serialization.read[List[LineItem]](request.contentString)

    val order = shop.placeOrder(items)
    response.ok( Serialization.writePretty(order) )
  }

  get("/orders/:id") { request: Request =>
    shop.getOrder(request.params("id")) match {
      case Some(order) => response.ok.json(order)
      case None => response.notFound
    }
  }

  post("/supply") { request: Request =>
    val items = Serialization.read[List[LineItem]](request.contentString)

    items.foreach { item =>
      warehouse.supply(item.product, item.count)
    }

    response.ok
  }
}
