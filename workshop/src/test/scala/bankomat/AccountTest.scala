package bankomat

import org.scalatest.{FunSuite, MustMatchers}

class AccountTest extends FunSuite with MustMatchers {

  test("An account can deposit an amount of money") {
    val account = Account("7411", BigDecimal("100.4500"))
    account.deposit(BigDecimal("4.0")).balance mustBe BigDecimal("104.4500")
  }

  test("An account can deposit an amount of money and rounds correct") {
    val account = Account("7411", BigDecimal("100.9999"))
    account.deposit(BigDecimal("0.00004")).balance mustBe BigDecimal("100.9999")
  }

  test("An account can deposit an amount of money and rounds up correct") {
    val account = Account("7411", BigDecimal("100.9999"))
    account.deposit(BigDecimal("0.00005")).balance mustBe BigDecimal("101.0000")
  }

  test("An account can withdraw an amount of money") {
    val account = Account("7411", BigDecimal("100.4500"))
    account.withdraw(BigDecimal("20.4500")).balance mustBe BigDecimal("80.0000")
  }

  test("An account can withdraw an amount of money and rounds correct") {
    val account = Account("7411", BigDecimal("100"))
    account.withdraw(BigDecimal("0.00004")).balance mustBe BigDecimal("100")
  }

  test("An account can withdraw an amount of money and rounds up correct") {
    val account = Account("7411", BigDecimal("100"))
    account.withdraw(BigDecimal("0.00005")).balance mustBe BigDecimal("99.9999")
  }
}
