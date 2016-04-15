package bankomat

import java.io.{File, FileReader, PrintWriter}

import net.liftweb.json.{Serialization, _}
import org.scalatest.{FunSuite, GivenWhenThen, MustMatchers}

class SafeTest extends FunSuite with MustMatchers with GivenWhenThen {

  implicit val formats = DefaultFormats + new BigDecimalSerializer

  test("The safe can persist a map of accounts") {
    Given("A temp file and a safe")
    val file = File.createTempFile("safe", ".json")
    val safe = new Safe(file)

    And("Example accounts")
    val accounts = Map(
      "0815" -> Account("0815", BigDecimal("200")),
      "4711" -> Account("4711", BigDecimal("250"))
    )

    When("The acounts are persisted")
    safe.persist(accounts)

    Then("The file has all the accounts")
    val map = Serialization.read[Map[String, Account]](new FileReader(file))

    map mustBe accounts
  }

  test("The safe can load a map of accounts") {
    Given("A temp file and a safe")
    val file = File.createTempFile("safe", ".json")
    val safe = new Safe(file)

    And("Example accounts as json file")
    val accounts = Map(
      "0815" -> Account("0815", BigDecimal("200")),
      "4711" -> Account("4711", BigDecimal("250"))
    )

    implicit val formats = DefaultFormats + new BigDecimalSerializer
    val printer = new PrintWriter(file)
    printer.write(Serialization.write(accounts))
    printer.flush()
    printer.close()

    When("The acounts are persisted")
    val actualAccounts = safe.load

    Then("The file has all the accounts")
    actualAccounts mustBe accounts
  }
}
