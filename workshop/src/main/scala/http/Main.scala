package http

import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.finatra.http.routing.HttpRouter
import http.di.RuntimeEnvironment
import http.shop.Product

object Main extends Server with RuntimeEnvironment {
  val nutella = Product("1", "Nutella", 2.49)
  val schabau = Product("2", "Schabau 0,7l", 6.49)
  val käse = Product("3", "Käse", 2.49)
  val ötte = Product("4", "Kasten Ötte", 6.99)
  val kinderriegel = Product("5", "Kinderriegel", 6.99)

  warehouse.supply(nutella, 30)
  warehouse.supply(schabau, 40)
  warehouse.supply(käse, 100)
  warehouse.supply(ötte, 20)
  warehouse.supply(kinderriegel, 60)
}

class Server extends HttpServer {
  override val defaultFinatraHttpPort: String = ":8080"

  override protected def configureHttp(router: HttpRouter): Unit = {
    val shopController: Controller = new ShopController with RuntimeEnvironment

    router.add(shopController)
  }
}