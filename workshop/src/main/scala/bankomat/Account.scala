package bankomat

import scala.math.BigDecimal.RoundingMode

case class Account(number: String, balance: BigDecimal) {
  val scaledBalance = scale(balance)

  def deposit(amount: BigDecimal): Account = {
    copy(balance = balance + scale(amount))
  }

  def withdraw(amount: BigDecimal): Account = {
    copy(balance = balance - scale(amount))
  }

  private def scale(value: BigDecimal): BigDecimal = value.setScale(4, RoundingMode.HALF_UP)
}
