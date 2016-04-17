package http.di

import http.shop.Shop
import http.shop.warehouse.Warehouse

trait Environment {
  val shop: Shop
  val warehouse: Warehouse
}
