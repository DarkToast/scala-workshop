package http.shop

import java.util.{Date, UUID}

case class LineItem(product: Product, count: Int)

case class Order(id: String, date: Date, lineItems: List[LineItem], status: String, billing: Option[Billing] = None)

case class Billing(limeItems:List[LineItem], amount: Double) {
  val id: String = UUID.randomUUID().toString
}