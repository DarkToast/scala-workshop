/********************
 * IMPORTS
 ********************/
// Wie in Java, so sind auch die Klassen und Objekte von Scala in Packages organisiert.
// Auch hier gilt: Packagename gleich Verzeichnisname. Unterpackage gleich Unterverzeichnis.
// Ein Import von

// Packagedefinition der in dieser Datei liegenden Klassen.
package section2

// Wir können in Scala eine beliebige Klasse importieren. Hier `ArrayList` aus der Java Standard-Lib
import java.util.ArrayList

// Auch kann man statische Elemente über einen Import direkt Nutzbar machen.
// Hier die statische `help1` Methode aus dem `Helper` Object
import section2.Helper.help1

// Möchte man mehrere Klassen aus einem Package importieren, so kann man entweder mehrere Importstatements
// schreiben, oder mit einem Statement, kommasepariert, mehrere Klassen angeben:
import java.util.{Base64, Random}

// Alle Klassen eines Pakets, oder alle statischen Elemente eines Objecs, können mit dem Wildcardzeichen `_`
// importiert werden.
// Importiert alle statischen Elemente der Klasse `java.lang.Math`
import java.lang.Math._

// Hin und wieder kommt man in die Bredouille, eine Klasse mit dem selben Namen importieren zu müssen, wie eine
// aus dem aktuellen Paket. Ein schönes Beispiel sind die gleich heißenden `List` Klassen aus `java.util`
// und `scala.collection.immutable`. Oder man findet einfach keinen besseren Namen für seine Klasse wie das
// gerade implementierte Trait.
// Man kann natürlich den vollen Namen mit Paketnamen bei jeder Verwendung nehmen,
// oder man definiert einen Alias für eine Klasse im aktuellen Scope:
// Hier wird der Alias `JavaList` für die Klasse `java.util.List` definiert.
import java.util.{List => JavaList}

object Helper {
  def help1() = println("help1")
  def help2() = println("help2")
}

class Imports {
  val jList: JavaList[String] = new ArrayList[String]()
  help1()

  def randomString = Base64.getEncoder.encode(new Random().nextInt().toString.getBytes())

  abs(-5)

  // Ein Import ist in Scala nicht auf den oberen Teil einer Datei beschränkt. Imports können zu jeder Zeit
  // in einem Scope eingesetzt werden. Er ist dann auch nur in genau diesem Scope nutzbar.
  import Helper.help2
  help2()
}
