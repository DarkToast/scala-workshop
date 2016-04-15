package section3

/*************
 * Implicit Parameter und Variablen.
 *************/

// Implicit Parameter und Variablen sind gut geeignet, immer wiederkehrende Parameter
// wie session Ids, Contextobjekte, etc. durch mehrere Methoden- und Objeketschichten
// zu schleifen.

// Zunächst haben wir hier eine Klasse, welche von der public Methode `step1`
// ausgehend drei Methoden nach einander ausführt.
// Jede Methode wird mit `x: String` und `y: Int` aufgerufen, wobei `y` über eine
// zweite Parameterliste gesetzt wird und das keyword `implicit` besitzt.
class SomeFooClass {

  // `step1` hat nun den implicit value y.
  // `step2` wiederum hat einen implicit parameter.
  def step1(x: String)(implicit y: Int) {
    // Da beide Werte (`y` und der zweite Parameter von `step2`) implicit sind
    // und vom gleichen Typ (Int) sind, kombiniert uns der Compiler beide
    // Werte und setzt sie uns impliziet an die richtite Stelle:
    step2(x)
    //    |
    // Hier wird eigentlich `step2(x)(y)` aufgerufen.
  }

  // Step2 wiederum macht das selbe wie step1 und gibt auch y an `step3` weiter.
  // Wichtig ist für die Zuordnung lediglich der Typ der Variable, nicht der Name.
  // Existieren zwei implicit vals vom selben Typ, wird uns der Compiler einen Fehler werfen.
  private def step2(x: String)(implicit y: Int) {
    step3(x)
  }

  // Step3 benutzt nun `theInt` für ein println.
  private def step3(theString: String)(implicit theInt: Int) {
    println(s"X is $theString and Y is: $theInt")
  }
}

object Implicits {

  def main(args: Array[String]) {
    // Ab jetzt benutzen wir unsere `SomeFooClass`.
    // Wir können den zweiten Parameter natürlich direkt
    // per zweiter Parameterliste übergeben, oder
    val foo = new SomeFooClass
    foo.step1("Hallo welt")(4)

    // Wir definieren uns direkt ein implicit val
    // und können den Wert auch hier direkt implizit verwenden.
    implicit val someInt = 24
    foo.step1("Hallo!")
  }

}


