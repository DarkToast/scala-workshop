package http.orders

import scala.collection.mutable
import scala.concurrent.Future

case class StoreItem(product: Product, count: Int)

class Warehouse {

  val store: mutable.Map[String, StoreItem] = mutable.Map()

  def supply(product: Product, count: Int): Future[StoreItem] = Future {
    store.get(product.id) match {
      case None => store(product.id) = StoreItem(product, count)
      case Some(item) => store(product.id) = item.copy(count = item.count + count)
    }

    store(product.id)
  }

  def fulfillOrder(order: Order): Future[Order] = ???
}
