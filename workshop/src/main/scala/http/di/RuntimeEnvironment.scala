package http.di

import http.shop.{Shop, ShopInstance}
import http.shop.warehouse.{Warehouse, WarehouseInstance}

trait RuntimeEnvironment extends Environment {
  override lazy val shop: Shop = ShopInstance
  override lazy val warehouse: Warehouse = WarehouseInstance
}
