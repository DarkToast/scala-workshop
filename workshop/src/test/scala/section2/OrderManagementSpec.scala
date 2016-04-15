package section2

import org.scalatest.FunSuite

class OrderManagementSpec extends FunSuite {
  import OrderManagement._

  test("Credit cards are valid") {
    assert(hasValidPayment(1) === true)
  }

  test("Credit cards with an expired expiration date is not valid") {
    assert(hasValidPayment(4) === false)
  }

  test("Debit cards are valid") {
    assert(hasValidPayment(5) === true)
  }

  test("The DebitCard with the accountNr '54321' is on the company blacklist") {
    assert(hasValidPayment(3) === false)
  }

  test("An unknown order can not have a valid payment") {
    assert(hasValidPayment(999) === false)
  }

  //----------------

  test("An order with apples and mild can be purchaes by non adults.") {
    assert(onlyAdultPurchase(2) === false)
  }

  test("An order with beer can only purchased by adults") {
    assert(onlyAdultPurchase(1) === true)
  }

  test("An unknown order can not be an adult purchase") {
    assert(onlyAdultPurchase(999) === false)
  }

  //----------------

  test("The price of the first order is 48.00") {
    assert( orderPrice(1) === Some(48.00) )
  }

  test("The price of the fourth order is 28.00") {
    assert(orderPrice(4) === Some(28.00))
  }

  test("To determine the price of an unknown order results in a None value") {
    assert(orderPrice(999) === None)
  }

  //----------------

  test("The christmas business was 30.00 €") {
    assert(christmasBusiness() === 30.00)
  }
}
