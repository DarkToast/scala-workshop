package http.shop.warehouse

import http.shop.{Billing, Order, Product, LineItem}

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class StoreItem(product: Product, count: Int)

class Warehouse {

  val store: mutable.Map[String, StoreItem] = mutable.Map()

  def products: Future[List[Product]] = Future {
    Thread.sleep(1000)
    store.values.map(item => item.product).toList
  }

  def supply(product: Product, count: Int): Future[StoreItem] = Future {
    store.synchronized {
      store.get(product.id) match {
        case None => store(product.id) = StoreItem(product, count)
        case Some(item) => store(product.id) = item.copy(count = item.count + count)
      }
    }

    store(product.id)
  }

  def fulfillOrder(order: Order): Future[Billing] = Future {

    def fulfillLineItem(item: LineItem): Option[LineItem] = {
      val productId = item.product.id

      if (store.keySet.contains(productId)) {
        store.synchronized {
          val storeItem = store(productId)
          if (storeItem.count > item.count) {
            store(productId) = storeItem.copy(count = storeItem.count - item.count)
            Some(item)
          } else {
            store - productId
            Some(item.copy(count = storeItem.count))
          }
        }
      }
      else {
        None
      }
    }

    Thread.sleep(30000)

    val fulfilledLineItems = order.lineItems
      .map(item => fulfillLineItem(item))
      .filter(_.isDefined)
      .map(_.get)


    Billing(fulfilledLineItems, fulfilledLineItems.map(item => item.product.price * item.count).sum)
  }
}
