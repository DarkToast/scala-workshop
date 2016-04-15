package bankomat

import scala.collection.mutable

class Bank(private val safe: Safe) {
  val accounts = mutable.Map() ++ safe.load

  def createAccount(accountNo: String) = {
    require(accounts.get(accountNo).isEmpty, "Account already exists.")

    accounts(accountNo) = Account(accountNo, 0)
    safe.persist(accounts.toMap)
  }

  def exists(accountNo: String): Boolean = {
    accounts.get(accountNo).isDefined
  }

  def deposit(accountNo: String, amount: BigDecimal) = {
    accounts(accountNo) = accounts(accountNo).deposit(amount)
    safe.persist(accounts.toMap)
  }

  def withdraw(accountNo: String, amount: BigDecimal) = {
    accounts(accountNo) = accounts(accountNo).withdraw(amount)
    safe.persist(accounts.toMap)
  }

  def transfer(from: String, to: String, amount: BigDecimal) = {
    accounts(from) = accounts(from).withdraw(amount)
    accounts(to) = accounts(to).deposit(amount)
    safe.persist(accounts.toMap)
  }
}
