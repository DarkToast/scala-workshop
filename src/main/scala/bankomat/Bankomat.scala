package bankomat

import java.io.File


object Bankomat {
  val file = new File("./bank.json")
  val bank = new Bank(new Safe(file))

  def main(args: Array[String]) {
    if(args.length <= 0) {
      printUsage()
    } else {
      args.toList match {
        case List("create", accountNo) =>
          if(!bank.exists(accountNo)) {
            bank.createAccount(accountNo)
          } else {
            println(s"The account '$accountNo' already exists.")
          }
        case List("deposit", accountNo, amount) =>
          checkedOperation(accountNo, amount, (acc, amount) => bank.deposit(acc, amount))
        case List("withdraw", accountNo, amount) =>
          checkedOperation(accountNo, amount, (acc, amount) => bank.withdraw(acc, amount))
        case _ => printUsage()
      }
    }
  }

  def checkedOperation(accountNo: String, amount: String, op: (String, Double) => Unit) = {
    try {
      if(bank.exists(accountNo)) {
        op(accountNo, amount.toDouble)
      } else {
        println(s"Error: The account '$accountNo' does not exist.")
      }
    } catch {
      case e: NumberFormatException => println(s"Error: '$amount' must be a double value. eg: 1.34")
    }
  }

  def printUsage(): Unit = {
    println(
      """
        | Usage:
        |   create #accountNo
        |   deposit #accountNo #amount
        |   withdraw #accountNo #amount
        |
      """.stripMargin)
  }
}

