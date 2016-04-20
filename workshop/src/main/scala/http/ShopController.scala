package http

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import http.di.Environment
import http.shop.LineItem
import net.liftweb.json.{Serialization, _}

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class ShopController extends Controller {
  this: Environment =>

  implicit val formats = DefaultFormats

  get("/products") { request: Request =>
    val search = request.params.getOrElse("search", "")
    val resultF = Await.result(shop.search(search), Duration.Inf)
    response.ok.json(resultF)
  }

  get("/orders") { request: Request =>
    response.ok( Serialization.writePretty(shop.getOrders))
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
