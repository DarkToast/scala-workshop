import scala.util.{Failure, Success, Try}

// We can easily throw exceptions:
//throw new IllegalArgumentException("foobar")

// We can catch the exception with some case clauses:
// And also define a finally block:
try {
  throw new IllegalArgumentException("foobar")
} catch {
  case npe : NullPointerException => println("NullPointerException catched")
  case iae : IllegalArgumentException => println("IllegalArgumentException catched")
  case _ : Throwable => println("Unknown error raised")
} finally {
  println("Some finally code")
}

// Like many other scala, even a try is substituted to a result.
// But only from the catch clauses:
val result = try {
  throw new IllegalArgumentException("foobar")
} catch {
  case npe : NullPointerException => "NullPointerException catched"
  case iae : IllegalArgumentException => "IllegalArgumentException catched"
  case _ : Throwable => "Unknown error raised"
} finally {
  "Will never be the value of `result`"
}

// By the way:
// The case block is just a partial function.
// So, we can reuse a generic catch block in many
// tries:
val exceptionHandler: PartialFunction[Throwable, String] = {
  case e : IllegalArgumentException => "foobar!"
}

val f = try {
  throw new IllegalArgumentException("foobar")
} catch exceptionHandler



// A more functional style to handle exceptions, is the Try monad, which is very
// similar to the Option type. The Try is of a type T, which may be the successful
// result. If the code does not thrown an exception, the try is a `Success[T]`, otherwise
// the try is a `Failure[Throwable]`:
val aTry: Try[String] = Try {
  throw new IllegalArgumentException("A new exception")
}

aTry match {
  case Success(myString) => println(myString)
  case Failure(exception) => println(exception)
}



