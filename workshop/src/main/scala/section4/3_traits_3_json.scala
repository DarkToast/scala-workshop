package section4

import java.util

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.{JsonIgnore, PropertyAccessor}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

/**
 * This is an example how to use traits to mixin json converting
 */

/**
 * We define our trait Json which can convert the class it was mixed in to
 * a json string. We use the jackson mapper and therefore must ignore some
 * fields.
 *
 * This class can produce a json value out of itself, with the self reference `this`.
 * As the trait will be inherited somewhere, the `this` reference always will be a
 * concrete object.
 */
trait Json {
  @JsonIgnore
  val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY)

  @JsonIgnore
  def toJson: String = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this)
}

/**
 * We define two simple case classes.
 * At this point, only `Address` knows about any json mangling.
 * `Customer` is just a plain stand alone case class.
 */
case class Address(street: String, city: String) extends Json
case class Customer(name: String, surname: String, address: Option[Address])

/**
 * We now can create some instances of the case classes.
 */
object JsonTraitMixin extends App {

  // Address will have the `toJson` method as it inherits the `Json` trait.
  val address = new Address("Fakestreet", "Bonn")

  // As a special feature of traits, we now can mixin the trait on the fly by the instantiation of
  // a new `Customer` to make this instance, and only this instance, "jsonable":
  val person = new Customer("Max", "Mustermann", Some(address)) with Json

  println("\n\nAddress\n=================")
  println(address.toJson)
  println("\n\nCustomer\n=================")
  println(person.toJson)

  // This pattern also works for all classes and is a good way to expand third party classes
  // with new functionality:
  val person1 = new Customer("Max", "Mustermann", Some(address))
  val person2 = new Customer("Hans", "Dampf", Some(address))

  val javaList = new util.ArrayList[Customer] with Json
  javaList.add(person1)
  javaList.add(person2)

  println("\n\njava.util.List\n=================")
  println(javaList.toJson)
}