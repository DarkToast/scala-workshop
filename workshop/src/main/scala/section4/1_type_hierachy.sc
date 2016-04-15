// In scala every object is inherits the class 'Any'
// 'Any' is the equals mechanism to Javas Object.
// But in scala even java.lang.Object inherits Any. ;-)
var a: Any = new java.lang.Object
a = "Hallo"
a = List(1, 2, 3)
a = true
a = 123


// Any, therefore, has two subclasses. AnyRef and AnyVal
// AnyVal is the superclass for all value classes in scala:
// Byte, Short, Char, Int, Long, Float, Double, Boolean and Unit
var b: AnyVal = ()
b = 34
b = true
b = 3.456
// y = List(1, 2,3) <-- Won't compile! List not an AnyVal!


// AnyRef is the superclass for all user defined objects
class Hello(hello:String)
var c: AnyRef = new Object
c = List(1, 2, 3)
c = "Hallo Welt"
c = new Hello("hallo")


// Scala also defines bottom types.
// The first reason is to give the null-reference a valid Type, cause anything in scala must have a type.
// The type 'Null' inherits from ALL objects, which therefore inherits from AnyRef.
var d: Null = null
var e: String = null // <- works cause Null inherits all AnyRefs
var f: Hello = null // <- works also cause AnyRef <- Hello <- Null


// The second bottom type is the 'Nothing' type. Nothing is the subtype of ALL other types!
// Nothing has no reference! So why is this type present?
// We can use this type to build full type independent constructs:
abstract class A[T] {
  def someMethod: T
}

class ErrorThrowerOfA extends A[Nothing] {
  def someMethod: Nothing = throw new RuntimeException
}

// Example of 'programming in scala':
// Some all mighty error method
def error(message: String): Nothing = throw new RuntimeException(message)

def divide(x: Int, y: Int): Int =
  if(y != 0) x / y
  else error("Can not divide by zero!") // <- We "return" a Nothing and therefore can us it as a return type of the Int method.

divide(3, 0)

// A practical example is the None subtype of Option.
val x: Option[Nothing] = None
/*
  /** This case object represents non-existent values.
   *
   *  @author  Martin Odersky
   *  @version 1.0, 16/07/2003
   */
  case object None extends Option[Nothing] {
    def isEmpty = true
    def get = throw new NoSuchElementException("None.get")
  }
*/








