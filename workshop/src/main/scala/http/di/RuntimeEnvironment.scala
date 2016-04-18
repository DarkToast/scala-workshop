package http.di

import http.shop.{Shop, ShopInstance}
import http.shop.warehouse.{Warehouse, WarehouseInstance}

object ShopInstance extends Shop with RuntimeEnvironment
object WarehouseInstance extends Warehouse

trait RuntimeEnvironment extends Environment {
  override lazy val shop: Shop = ShopInstance
  override lazy val warehouse: Warehouse = WarehouseInstance
}
