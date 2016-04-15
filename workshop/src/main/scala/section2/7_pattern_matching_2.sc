import scala.Predef._

/** Variable binding */

// Nutzt man ein constructor pattern und will nicht nur auf die einzelnen
// Elemente zugreifen, sondern mit dem gesamten Objekt arbeiten, so
// bietet sich das variable binding an.

// Zunächst eine Beispielklasse:
abstract class Drivable

case class Car(colour: String, brand: String, km: Double) extends Drivable {
  def startEngine() = println("WROOOMM")
}

// Möchten wir nun auf einen BMW matchen und zusätzlich im Nachhinein
// diesen Wagen auch mit `startEngine` starten, so können wir das ganze
// Objekt mit einer Variable belegen: `varName @ [Pattern]`:
Car("blue", "BMW", 30000) match {
  case c @ Car(_, "BMW", _) => println(c.startEngine())
}

// Alternativ kann man natürlich auch die Variable aus dem oberen Scope benutzen.
// Hin und wieder ist das aber nicht möglich. Gerade, wenn man erst durch das Pattern
// Matching den entsprechenden Subtyp erfährt:
val x: Drivable = Car("blue", "Audi", 80000)
x match {
                            // `x` ist zwar auch in diesem Scope erreichbar,
                            // hat aber den falschen Typ.
  case c @ Car(_, "BMW", _) => println(c.startEngine())
}



/** Pattern guards */
// Möchte man ein pattern noch etwas verfeinern, so kann man, kombiniert
// mit dem variable binding, diesem ein pattern guard beigeben.
// Dabei habdelt es sich einfach um ein if, welches direkt nach dem Pattern,
// also noch vor dem `=>` geschrieben wird:
Car("blue", "BMW", 30000) match {
  case Car(_, "Audi", km) if km < 20000 => println("Adult male")
  case Car(_, "BMW", _) => println("childish male")
}



/** Tuple patterns */
// Tuples können ähnlich wie case classes über ein Konstruktor pattern gematched werden:
(1, 2, 3) match {
  case (_, _, 2) => println("_, _, 2")
  case (_, 2, 4) => println("_, 2, 4")
  case (1, _, _) => println("1, _, _")
}



/** Typed patterns */
// Typed patterns ermöglichen es, anhand des Datentyps zu filtern.
// `typeMatcher` arbeitet mit einem Value vom Typ `Any`. Im Matchingblock überprüfen wir nun den
// eigentlichen Typ des Values. Die Syntax ist hier wie bei einer Variablendeklaration. Also
// `name`: `type`. Alternativ kann als `name` auch das Wildcardzeichen benutzt werden, wenn uns nur der
// Typ interessiert:
def typeMatcher(x: Any) = x match {
  case theString: String => println("A String with value: " + theString)
  case theInt: Int => println("An Integer with value: " + theInt)
  case _: Boolean => println("A Boolean value")
  case _ => println("Something other")
}
typeMatcher("Hallo")
typeMatcher(43)
typeMatcher(true)
typeMatcher(34.5)

// WARNUNG: Type erasures!!! Oder: Ein kleiner Exkurs in die tiefen Tiefen der JVM.
// Auf der JVM sind Generic Informationen lediglich zur Compilezeit bekannt.
// Zur Laufzeit sind diese Informationen nicht mehr vorhanden. Eine List[Int]
// ist dort äquivalent zu List[String].
// Beim Pattern Matching ist man schnell versucht ein Typpattern mit Generics
// zu versuchen. Wie im folgenden Beispiel eine Map, deren Values Integers sind,
// deren Keytyp uns aber egal ist:
def isIntCollection(x: Any) = x match {
  case m: Map[_, Int] => true
  case a: Array[Int] => true
  case _ => false
}

// Durch das type erasure wird nun folgender Ausdruck zu true ausgewertet,
// obwohl wir definitiv eine Map[String, String] als Parameter geben.
// Das Problem des type erasures tritt auch in Java auf. Siehe:
//    http://docs.oracle.com/javase/tutorial/java/generics/bridgeMethods.html
isIntCollection(Map("x" -> "y"))

// Aaaaaaber. Was passiert, wenn wir ein `Array("hallo")`, also ein Array[String]
// übergeben? Seltsamerweise erhalten wir hier ein `false` von der Methode.
//
// Arrays stammen noch aus einer Zeit der JVM als es noch keine Generics gab. Sie sind
// daher ein Spezielfall, da sie die einzige Collectionstruktur sind, die direkt von der JVM
// unterstützt werden und nicht erst programmatisch erzeugt werden müssen.
// Arrays sind auch die einzige Collection, die zur Laufzeit ihren Typ
// behält. Ein Java `int[]` ist eben ein eigener Typ und äquivalent zu dem Scala Array[Int].
// s. auch: http://code.stephenmorley.org/articles/java-generics-type-erasure/
isIntCollection(Array("hallo"))



/** Patterns everywhere */
// Das Konstruktorpattern kann auch direkt bei einer Variablendekleration benutzt werden.
// Hier extrahieren wir die Farbe des Autos mit einem Konstruktorpattern
// und speichern den Wert in der Variable `color`, welche wir im Anschluss direkt
// benutzen können.
val car = Car("red", "Jaguar", 20000)
val Car(colour, _, _) = car
println(colour)

// Das Autobeispiel wäre vermutlich mit einem `car.colour` schneller und nachhaltiger
// gewesen. Tatsächlich ist diese Variablendeklaration auch eher für ein einfach und
// übersicherlicheres extrahieren von Tupledaten geeignet:
// Das geht meist einfacher von der Hand als ein
//   val t = getTuple()
//   println(t._1)
def getTuple(): (String, Int) = ("hallo", 123)
val (string, number) = getTuple()

println(string)
println(number)