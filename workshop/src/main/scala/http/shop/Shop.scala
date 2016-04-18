package http.shop

import java.util.{Date, UUID}

import http.di.{Environment, RuntimeEnvironment}

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Shop {
  this: Environment =>

  val orders: mutable.Map[String, Order] = mutable.Map()

  def search(beginsWith: String): Future[List[Product]] = {
    warehouse.products.map{ list =>
      list.filter(product => beginsWith.isEmpty || product.name.startsWith(beginsWith))
    }
  }

  def placeOrder(lineItems: List[LineItem]): Order = {
    val order = Order(UUID.randomUUID().toString, new Date(), lineItems, "open")
    orders(order.id) = order

    warehouse.fulfillOrder(order).onSuccess {
      case billing: Billing => orders(order.id) = order.copy(billing = Some(billing), status = "fulfilled")
    }

    order
  }

  def getOrder(id: String): Option[Order] = orders.get(id)
}
