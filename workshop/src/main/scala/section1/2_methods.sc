import scala.annotation.tailrec
import scala.util.Random

/************************
 * METHODS
 ************************/

/** Grundsätzlicher Aufbau */

// Methoden funktionieren in Scala genauso wie in Java. Sie besitzen einen Namen,
// haben keinen bis mehrere Eingabeparameter und keinen oder einen Rückgabewert.
//
//                             method block
//                             |
//                           Gleichheitszeichen(vergisst man leicht)
//                           | |
//                        return type
//                        |  | |
//              second parameter
//              |         |  | |
//      first parameter   |  | |
// Name |       |         |  | |
//   |  |       |         |  | |
def sum(x: Int, y: Int): Int = {
  val z = x + y
  z // Der letzte Ausdruck eines Methodenblocks ist immer auch der Rückgabewert.
}

// Auch bei Methoden können wir die Schreibweise ein wenig verkürzen.
// Haben wir nur eine sehr kurze Methode, können die Klammern weg gelassen werden.
// Auch der Returntype kann vom Compiler implizit ermittelt werden.
// Achtung: Natürlich kann man manche Sachen weg lassen, allerdings macht es das für einen
// Nutzer der Methode nicht unbedingt einfacher sie zu verstehen. Als best practise kann man nehmen:
// Öffentliche Methoden so explizit wie möglich halten.
def sum2(x: Int, y: Int) = x + y


// Auch wenn Methoden möglichst seiteneffektlos sein und einen Returntype haben sollten,
// so ist natürlich auch der leere Returntype möglich. Was in Java und C das void, ist in Scala
// die Klasse Unit.
def print(x: String): Unit = {
  println(x)
}



/** Parameter */
// Methoden können einen repeated parameter beinhalten, wodurch beliebig lange
// Parameterlisten möglich sind. Dieser Parameter muss immer am Ende der Parameterliste
// stehen und bildet innerhalb des Codeblocks eine Sequence
// (Die Oberklasse von List, ArrayBuffer, etc.)
def repeatedParameters(values: String*): String = values.mkString(" ")
repeatedParameters("Hallo", "Welt", "!!!")


// Beim Aufruf einer Methode kann man die Parameternamen mit angeben
// und somit zum Beispiel längere Parameterlisten übersichtlicher gestalten,
// oder auch die Parameter in anderer Reihenfolge angeben.

def namedArguments(value: String, splitAt: Char): Array[String] = value.split(splitAt)

namedArguments(splitAt = '.', value = "Hallo.Welt.!!!")


// Für jeden Parameter kann ein Deaultwert definiert werden. Dieser wird immer dann
// gesetzt, wenn beim Aufruf dieser Parameter leer bleibt.
def split(value: String, splitAt: Char = '.'): Array[String] = value.split(splitAt)
split("Hallo-Welt-!!!", '-')
split("Hallo.Welt.!!!")



/** Parameterlisten */
// Methoden können nicht nur eine Parameterliste besitzen, sondern mehrere abgetrennte.
// Dies ermöglicht für die spätere Nutzung z.B. einen mehrzeilige Ausdrücke als letzten Parameter
// zu übergeben.
def splitSum(x: Int)(y: Double) = x + y

splitSum(10) {
  Math.sqrt(Random.nextInt(100))
}


/** Methoden in Methoden */
// Methode müssen nicht zwangsläufig an Klassen gebunden sein. Methoden können auch innerhalb
// eines anderen Methodenblocks definiert werden.
// Als komplexeres Beispiel hier eine Methode zum Summieren von Zahlen, welche innerhalb
// eine weitere rekursive Methode benutzt.
def recursiveListSum(list: List[Int]) = {
  @tailrec
  def step(sum: Int, elements: List[Int]): Int = {
    if(elements.isEmpty) {
      sum
    } else {
      step(sum + elements.head, elements.tail)
    }
  }

  step(0, list)
}
recursiveListSum(List(1, 2, 3, 4, 5))
