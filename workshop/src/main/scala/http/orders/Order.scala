package http.orders

import java.util.Date

case class LineItem(product: Product, count: Int)

case class Order(id: String, date: Date, lineItems: List[LineItem], status: String)
