/*********************
 * PATTERN MATCHING
 ********************/

// Das pattern matching ist eines der mächtigsten Werkzeuge für Verzweigungslogik in Scala
// und stammt aus der Werzeugkiste der funktionalen Programmierung.
// Was man aus Java oder C als simples switch/case kennt, ist in Scala weit umfangreicher.

/** Simple match */
// Diese Methode erhält als ersten Parameter einen `String` x.
// Mit dem Operator `match` hinter einer Variable erzeugen wir nun einen neuen Matchingblock.
// Im folgenden Ausdruck werden dann die einzelnen Matchingausdrücke startend mit einem `case`
// erzeugt.
// Trifft ein Ausdruck auf den zu überprüfenden Wert zu, so wird der gesamte Matchingausdruck
// zu dem Wert hinter dem `=>` ausgewertet.
// Ein Fall Through findet hierbei nicht statt. Es gewinnt immer nur ein Match. Geprüft wird
// von oben nach unten.

def describe(x: String) = x match {
//      Matchingausdruck
//        |
//        |         Ergebnis, wenn der Match zutrifft
//        |         |
  case "Hallo" => "some hello"
  case "Hallo Welt" => "some hello world"
}

describe("Hallo Welt")    // Gibt "some hello world" zurück
describe("Hallo")         // Gibt "some hello" zurück
describe("Foo")           // Wirft eine `MatchError` Exception.



/** Constant pattern und default value mit dem wildcard pattern */
// Diese Methode bekommt einen Wert vom Typ `Any`. Das ist die Oberklasse aller Werte in Scala.
// Wie im oberen Beispiel matchen wir auch hier auf konstante Werte. Scala erlaubt uns
// jeden beliebigen Wert als Kriterium zu nehmen.
// Sollte kein Kriterium auf den übergebenen Wert passen, so bedienen wir uns hier dem
// Wildcardzeichen `_` was auf alle Werte passt und uns so einen Defaultwert ermöglicht.
def describe2(x: Any) = x match {
  case 10 => "ten"
  case true | false => "boolean"
  case "hello" => "hi!"
  case Nil => "empty List"
  case _   => "something else"  // <--- Wildcard pattern mit einem default value
}

describe2(10)     // ten
describe2(true)   // boolean
describe2(Unit)   // Something else



/** Variable pattern */
// Statt des `_` Zeichens können wir auch einen beliebigen Variablennamen nehmen.
// Dadurch wird der uns unbekannte Wert in diese Variable geschrieben und kann
// im folgenden Ausdruck verwendet werden.
42 match {
  case 0 => println("zero")
  case someVar => println(someVar)
}



/** Constructor pattern mit case classes */
// Case classes haben neben ihren vordefinierten Methoden noch einen sehr praktischen Vorteil.
// Sie können direkt in einem Machtingausdruck als Pattern benutzt werden.
// Zunächst definieren wir uns eine `User` Klasse und eine kleine Hierachie für das Geschlecht.
abstract class Gender
object Male extends Gender
object Female extends Gender

case class User(name: String, surname: String, age: Int, gender: Gender)

// Max und Martina stehen uns für ein Beispiel zur Verfügung.
val max = User("Max", "Mustermann", 34, Male)
val martina = User("Martina", "Musterfrau", 16, Female)


// Mittels des Constructor Patterns können wir Instanzen von case classes direkt auf den
// Ausdruck ihrer Erzeugung matchen. Dadurch lassen sich vielfältige Möglichkeiten für
// Fallunterscheidungen bewerkstelligen. Der Nachteil des Constructor Patterns ist, wenn sich der Konstruktor ändert,
// zum Beispiel ein neuer Parameter dazu kommt, müssen auch sämtliche Pattern matchings angepasst werden.
// Hier ein paar Beispiele:
def users(user: User) = user match {
  // Match auf einen User mit einem beliebigen Namen, den wir mit dem variable Pattern in die Variable
  // `name` schreiben. Der Nachname muss auf die Konstante `Mustermann` passen. Das Alter ist uns egal.
  // Daher der Wildcard. Das Geschlecht muss das Objekt `Male` sein. Trifft dies alles zu, führen das println aus.
  case User(name, "Mustermann", _, Male) => println(s"some male with surname Mustermann named: "+ name)

  // Dieses Pattern greift bei allen Usern, die weiblich sind. Auch hier benutzen wir wieder das variable pattern
  // Um den Namen zu extrahieren:
  case User(name, _, _, Female) => println(s"some female named: "+ name)

  // Zuletzt ein Matching auf alle männlichen User deren Alter 34 ist:
  case User(name, _, 34, Male) => println(s"some male named: "+ name)
}

users(max)
users(martina)



