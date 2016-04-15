package section2

import net.liftweb.json.{DefaultFormats, Formats, Serialization}
import spray.json.DefaultJsonProtocol

object JsonSerialization {


  case class Address(street: String, number: Int)
  case class User(name: List[String], age: Int, address: Address)


  def main(args: Array[String]) = {
    val user = User(List("Max", "Theoodor", "Mustermann"), 45, Address("Fakestreet", 123))

    liftJson(user)
    sprayJson(user)
  }


  /**
    * Json serialization with the lift json library.
    * @param user
    */
  def liftJson(user: User) = {
    implicit val formats: Formats = DefaultFormats
    val json = Serialization.write(user)

    println(json)

    val newUser: User = Serialization.read[User](json)
    println(newUser)
  }

  /**
    * Json serialization with the spray json library
    * @param user
    */
  def sprayJson(user: User) = {
    object MyJsonProtocol extends DefaultJsonProtocol {
      implicit val addressFormat = jsonFormat2(Address)
      implicit val userFormat = jsonFormat3(User)
    }

    import MyJsonProtocol._
    import spray.json._

    val json: JsValue = user.toJson
    println(json.toString)

    val newUser: User = json.convertTo[User]
    println(newUser)
  }

}
