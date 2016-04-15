package section4

import java.util.Date

// A simple trait with two fields which must be 'implement' with a value.
// Also a default implementation of a method `sayHello`
trait Person {
  val name: String
  val gender: String
  def sayHello() = println(s"Hello! I'm $name!")
}


// We can "mix in" the trait with 'extends' or 'with'
// Scala has NO native multi inheritance!
class Student(val name: String, val gender: String) extends Person


// We can define another trait which we want to mix in into our subclass.
// This trait represents a user of a cafeteria. It has a budget, can buy food and
// deposit money onto the budget.
// This trait has only default implemented method. So is probably could also stand alone
// as a class. But we want to mix this one into other classes.
trait CafeteriaUser {
  private var budget: Int = 0

  def actualBudget = budget

  def buyFood(costs: Int) = if(budget - costs < 0) {
    throw new RuntimeException("You can not buy food! Not enough money!")
  } else {
    budget = budget - costs
  }

  def deposit(value: Int) = budget = budget + value
}

// So we define a class `Employee` which is a `Person` and also a `CafeteriaUser`:
// To do this, we extend the Person class and mix in the `CafertiaUser` with the keyword `with`.
class Employee(val name: String, val gender: String) extends Person with CafeteriaUser {
  val beginOfEmployment = new Date
}


object ## extends App {
  val student = new Student("max", "male")
  student.sayHello()

  val employee = new Employee("Susan", "female")
  employee.sayHello()
  employee.deposit(100)
  println(employee.actualBudget)

  // The trait and therefore our object has its own type
  var user: CafeteriaUser = employee
}
