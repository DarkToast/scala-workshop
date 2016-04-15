package http.orders

import scala.concurrent.Future

class Shop {
  def search(beginsWith: String): Future[List[Product]] = ???

  def placeOrder(lineItems: List[LineItem]): Order = ???

  def getOrder(id: String): Future[Order] = ???
}
