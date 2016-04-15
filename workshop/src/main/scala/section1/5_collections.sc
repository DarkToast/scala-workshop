import scala.collection.mutable

/*****************
 * TUPLES
 *****************/
// Tuple sind zusammengesetzte Werte, unterschiedlicher Typen.
// Anders als bei Sets oder Listen, ist die Reihenfolge und die Anzahl der Werte
// fest und kann nicht manipuliert werden.
// Tuples eigenen sich besonders als Hilfskonstrukt, um mehrere Einzelwerte
// einem Parameter zuzuordnen oder als Rückgabewert bereit zustellen.

val tuple: (Int, String, Double) = (1, "hallo", 3.3)
val otherTuple = ("hallo", true, List(1, 2, 3))

// Ein Zugriff auf die namenlosen Werte erfolgt über die '_n' Felder.
// Später werden wir mittels pattern matching eine elegantere Möglichkeit sehen,
// auf die Werte zuzugreifen.
println(tuple._1)
println(tuple._2)
println(tuple._3)



/**************
 * Arrays
 *************/
// Arrays funktionieren vom Prinzip her genauso wie wir sie aus Java kennen, nur dass sie in Scala
// ein eigenständiges Objekt sind.
// Zunächst erzeugen wir ein neues Arrayobjekt eines Typs und einer festen Länge.

val array = new Array[Int](10)

// Zugriff erlangen wir anders wie in Java nicht mit eckigen Klammern, sondern mit runden Klammern.
// Auch hier kennt Scala keinen separaten Zugriffsoperator for Arrays. Stattdessen wird hier die
// im Objekt liegende `apply` und `update`  Methode aufgerufen. `apply` und `update` sind ein magic names.
// Wir können diese Methode egal an welchem Objekt statt `instance.apply(34)` direkt mit
// `instance(34)` aufrufen. Dieses Pattern werden wir desöfteren wiedersehen.
array(0) = 42
array.update(1, 43)

// Ein illegaler Zugriff außerhalb des Wertebereichs mündet in einer ArrayIndexOutOfBoundsException
//array(11) = 44 <- ArrayIndexOutOfBoundsException

// Alternativ kann man auch ein vorausgefülltes Array definieren. Hier bedienen wir uns dem companion
// objects von Array, welche wir später kennen lernen werden. Kurz gesagt ist es der statische Nachbar der
// Klasse Array, wiederum mit einer `apply` Methode.
val newArray = Array(1, 2, 3, 4, 5)



/**************
 * Listen
 *************/

/** Grundlagen und Konkatination */
// Listen sind der dynamischer Nachbar des Arrays und können beliebig wachsen und auch schrumpfen. Anders
// als in vielen anderen Sprachen ist die Standardliste in Scala immutable. Das heißt, jede Operation, die
// ich auf einer Liste vorneme, erzeugt wiederum eine neue Liste

// Listen können auch hier mit einem companion object erzeugt werden.
val list: List[Int] = List(1, 2, 3, 4, 5, 6)

// Zugriff erlangen wir wieder mit der gekürzt geschriebenen `apply` Methode
list(4)

// Die Methoden der Liste heißen ein wenig seltsam, sollen aber eher einen Operatorcharakter haben.
// Daher schreibt man sie auch eher ohne Punkt und Klammern.
val addList_1 = 0 :: list     // Legt die 0 an den Anfang der neuen Liste
val addList_2 = list :+ 7     // Legt die 7 an das Ende der neuen Liste

// Mit der ::: Methode lassen sich zwei Listen konkatenieren.
val concatList = List(-2, -1, 0) ::: list

// Ein paar nützliche Methoden sind `head` und `tail`.
// `head` liefert das erste Element einer Liste. `tail` die Liste ohne das erste Element.
val head = list.head  // 1
val tail = list.tail  // (2, 3, 4, 5, 6)


/** Transformationen */
// Darüber hinaus verfügt List über diverse Transformationsmethoden, die
// über Lambdaausdrücke gesteuert werden.
list.filter(i => i > 3)       // Filtert alle Elemente raus, die nicht auf den Filter passen
list.map(i => i + 2)          // Transformiert jedes Element der Liste
list.takeWhile(i => i > 4)    // Liefert so lange Elemente von links nach rechts, bis der Lambdaausdruck true ergibt.

// foldLeft "klappt" die Liste von links nach rechts zusammen. Der erste Parameter bildet den Startwert des Akkumulators.
// Der zweite Parameter nimmt eine Funktion mit zwei Parametern entgegen. Der erste ist der Akkumulator, der zweite
// der aktuelle Wert der Liste
// Die Aufrufe des Lambdas sähen dann so aus:
  // 0 + 1 = 1
  // 1 + 2 = 3
  // 3 + 3 = 6
  // ...
list.foldLeft(0)( (accumulator, element) => accumulator + element)

// Oder, wenn man etwas fancy unterwegs ist, nimmt man den foldLeft 'Operator' `/:` mit einer kurzen Lambda. ;-)
(0 /: list)(_ + _)


/** map, flatten und flatMap */
// Im Fall einer verschachtelten Liste bietet List die Methode `flatten`, welches
// die inneren Elemente in die obere Liste verschiebt. Das Ergebnis vom Beispiel
// ist: List(Hallo, Welt, ., Hello, world, .)
val nestedList = List(List("Hallo", "Welt", "."), List("Hello", "world", "."))
nestedList.flatten

// Oft kommt die Kombination aus map und flatten vor. In diesem Beispiel transformieren
// wir jeden inneren Wert zu seiner Länge. Danach wird die Liste geflattet.
// Beide Methoden werden in `flatMap` vereint.
// Hat man sich irgendwann mal mit dem Konzept der Monaden beschäftigt, wird man merken
// das `flatMap` den bind-Operator abbildet.
val mappedList = nestedList.map(l => l.map(s => s.length))
mappedList.flatten    // --> List(5, 4, 1, 5, 5, 1)

nestedList.flatMap(l => l.map(s => s.length))   // --> List(5, 4, 1, 5, 5, 1)



/** Mutable List */
// Die Scala Collections bieten neben den normalen immutable Klassen auch mutable
// Alternativen. Für die Liste gibt es daher das ListBuffer Pendant.
val listBuffer  = mutable.ListBuffer[String]()
listBuffer += "hallo"
listBuffer += "welt"



/**************
 * Maps
 **************/
// Die standard Map ist wie die List eine immutable Datenstruktur. Auch die Map besitzt ein companion
// object, mit dem eine neue Map erzeugt werden kann.

// Key Value Paare werden dabei mit der implicit `->` Methode erzeugt. Diese Methode ist in unserem Fall nicht
// der Klasse String zugeordnet. Stattdessen wird der Key-String implizit in einen neuen Wert vom Typ
// `ArrowAssoc` umgewandelt, welcher die -> Methode hat. Diese wandelt ein `A -> B` Ausdruck in einen Tuple (A, B)
// um. Dieses Verfahren nennt sich `implicit conversion`. Java kennt das in rudimentärer Form als Auto(un)boxing.
// Dadurch ist zum Beispiel auch sowas möglich: `4 + " hallo"`
val firstMap = Map("one" -> "hallo", "two" -> "welt")
firstMap("one")

// Concatenations
val a_map = firstMap + ("three" -> "foo")
val b_map = a_map - "three"

// Wie auch bei der List, gibt es auch zu der immutable Map ein mutable Pendant aus dem
// Package scala.collection.mutable
val mutableMap: mutable.Map[String, Int]  = mutable.Map()
mutableMap += ("foo" -> 1) += ("bar" -> 2)