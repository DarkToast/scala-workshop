package http.di

import http.shop.Shop
import http.shop.warehouse.Warehouse

object ShopInstance extends Shop with RuntimeEnvironment
object WarehouseInstance extends Warehouse

trait RuntimeEnvironment extends Environment {
  override lazy val shop: Shop = ShopInstance
  override lazy val warehouse: Warehouse = WarehouseInstance
}
