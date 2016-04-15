package section3

object PartialFunctions  {
  def main(args: Array[String]) {

    // A partial function is a partially defined function.
    // These things, we may know from the math lessons in our school.
    // e.g: f(x) = 1/x is not defined for 0 but for all other numbers.

    // So, in scala a partial function is a function, which is not defined
    // for all input value of a type. Partial functions also can only take
    // a single parameter but have of course a return type.

    // For example this function takes a Option of String, and returns a String.
    // But the function only takes Some values. For Nones this function is not defined.
    // By the way, even if a partial function looks like a pattern match, it is not!
    val iHandleSomes: PartialFunction[Option[String], String] = {
      case Some(value) => value
    }

    val some = Some("hallo")
    val none = None

    // Therefore, partial functions have the `isDefinedAt` method, to test the value if it fits, before
    // we call the function itself.
    println(iHandleSomes.isDefinedAt(none))
    println(iHandleSomes.isDefinedAt(some))
    println(iHandleSomes(some))

    // But for what the heck do we need this function?
    // For example to define special handler, which we then can combine.
    // This handler, handles Nones.
    val iHandleNones: PartialFunction[Option[String], String] = {
      case None => "Fallback"
    }

    // We can combine both, the Some and the None handler to an all mighty Option handler:
    val optionHandler = iHandleSomes orElse iHandleNones

    println(optionHandler.isDefinedAt(none))
    println(optionHandler.isDefinedAt(some))
    println(optionHandler(some))

    /* Does not work:  Some("Welt") match (f3) */
    // But we can use them as exception mapper in a catch block
    val exceptionMapper: PartialFunction[Throwable, String] = {
      case e: IllegalArgumentException => "Fallback"
    }

    try {
      throw new IllegalArgumentException("Wah!")
    } catch exceptionMapper

  }
}
