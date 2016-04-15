/************************
 * CLASSES
 ************************/
/** Grundsätzlicher Aufbau */
// Die Klasse ist die Basis des objektorientierten Teils von Scala.
// Wie in Java hat eine Klasse einen Namen, einen oder mehrere Konstruktoren,
// Methoden und Attribute.

class ChecksumAccumulator {     // Klasse ChecksumAccumulator

  private var sum: Int = 0                // Attribut

  def add(b: Byte) { sum += b }           // Methode
  def checksum: Int = ~(sum & 0xFF) + 1   // Noch eine Methode
}

// Eine neue Instanz lässt sich mit dem `new` Operator erzeugen. Dies ist einer der wenigen nativen
// Operatoren. Die Klammern des leeren Konstruktors können wie bei einem "normalen" Methodenaufruf
// auch hier weg gelassen werden.
val ref = new ChecksumAccumulator()
ref.add(23)
ref.checksum



/** Konstruktoren */
// Der Hauptkonstruktor einer Klasse wird direkt hinter den Klassenbezeichner gesetzt.
// Es ist also keine explizite Methodendefinition notwendig. Nehmen wir hier noch die Möglichkeit
// der Defaultwerte für Parameter wahr, so lassen sich bereits viele Konstruktorfälle abbilden.

// Alle Felder in dem Hauptkonstruktor stellen auch gleichzeitig Klassenattribute dar.
// Daher sind vor jedem Parameter auch die üblichen Zugriffsmodifizierer (protected und private) möglich
// `public` ist immer standard und kann nicht explizit angegeben werden.
class ConstructorClass[T](private val x: Int, var y: Int, val z: Int = 20) {
//                     |   |                      |           |
//                     |   |                      |           |
//                     |   |                      |           |
//                     |   |                      |           public immutable mit default
//                     |   |                      |
//                     |   |                      public mutable variable
//                     |   |
//                     |   private immutable value
//                     |
//                     generic type parameter

  // Der gesamte Body der Klasse dient nun als Initialisierungsroutine der Klasse
  // Das heißt jeder Ausdruck zwischen den geschweiften Klammern wird zur Initialisierung
  // ausgeführt. Auf die Konstruktorargumente kann dabei direkt zugegiffen werden.
  println(s"Value $x set as value")

  private val sum = x+y+z

  def getSum: Int = sum


  // Sollten wir aber tatsächlich mal einen anderen Konstruktor brauchen, zum Beispiel, wenn wir
  // andere Parametertypen brauchen, können wir eine oder mehrere `this` Methoden definieren:
  def this(x: String, y: String) {
    this(x.toInt, y.toInt, 10)
  }
}

// Beispiele:
new ConstructorClass(1, 2)
new ConstructorClass("1", "2")
val ref2 = new ConstructorClass(1, 2, 3)

ref2.getSum   // Aufrufen der öffentlichen `getSum` Methode
ref2.y = 10   // Da `y` ein `public var` ist können wir das Feld von außen überschreiben.
ref2.z        // `z` ist ein public val. Daher können wir das Feld zwar lesen, aber nicht überschreiben.


/** Zugriffsmodifizierer */
// Wie in vielen anderen objektorientierten Sprachen, so können auch in Scala die Methoden und
// Attribute mit Zugriffsmodifizierer belegt werden. Standardmäßig sind alle Element `public`.
// Ein expliziertes `public` Bezeichner existiert daher nicht.
class Car() {
  /**
    * Startet den Motor. Die Methode ist public.
    *
    * Die Methode lässt sich von jedem Element aufrufen, die eine Referenz auf eine `Car`-Instanz
    * besitzt,
    */
  def startEngine(): Unit = {
    if(isOnTestBench) {
      println("Töf töf töf")
    } else {
      println("WRRROOOOOOOOOOOOMMMRRRÖÖÄÄÄRRRR")
    }
  }

  /**
    * Unfalltrigger mit einem anderen Fahrzeug.
    *
    * Der Zugriff ist hier `protected`. Die Methode kann also nur von
    * anderen `Car`s und deren abgeleiteten Klassen geschehen.
    *
    * @param other
    */
  protected def crashWith(other: Car) {
    // Tatsächlich können wir die Airbags des anderen Autos explodieren lassen,
    // da diese Methode "lediglich" private ist.
    other.explodeAirbags()
  }

  /**
    * Lässt die Airbags explodieren.
    *
    * Der Zugriff ist `private`. Die Methode kann also nur von anderen Instanzen
    * der Klasse `Car`. Abgeleitete Klassen sind hier außen vor.
    */
  private def explodeAirbags() = {
    println("PUFFF!")
  }

  /**
    * Diese Methode stellt fest, ob unser Auto auf einem Teststand steht und wir
    * die Abgaskontrolle speziell einstellen müssen. Natürlich wollen wir diese Methode
    * auf gar keinen Fall von außen erreichbar machen. Auch nicht von anderen `Car`s.
    * Denn sonst kommt uns noch die EPA auf die Schliche! :-O
    *
    * Dafür benutzen wir das private[this] Schlüsselwort. Damit ist die Methode nur aus der eigenen
    * Instanz aus aufrufbar.
    *
    * @return
    */
  private[this] def isOnTestBench: Boolean = {
    println("Shutting down turbo boost. Switching to lame duck mode.")
    true
  }
}

/** Objects */
// Scala Klassen kennen keine statischen Attribute oder Methode.
// Stattdessen gibt es ein komplett statisches Singleton Pendent zur Klassen, das `Object`.
// Ein Object lässt sich genau so erstellen wie eine Klasse, nur das es eben keinen Konstruktor hat.
object ThisIsAStaticObject {
  private val someField = 42

  def printField() = println(s"This is the field: $someField.")
}

// Der Zugriff erfolgt direkt über den Objektbezeichner.
ThisIsAStaticObject.printField()