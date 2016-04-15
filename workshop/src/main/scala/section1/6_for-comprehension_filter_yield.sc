/****
 * FOR COMPREHENSION
 *****/
// Definieren wir uns zunächst einmal eine kleine Int-Liste, die wir für die Beispiele nehmen werden.
val list = List(2, 4, 5, 8, 9)


/** Simple Iteration */
// Die for comprehension ist weit mehr als eine for Schleife in imperativen Sprachen.
// In Scala ist sie vielseitig einsetzbar und wird auch gerne als das schweizer Taschenmesser in Scala
// beschrieben.

// Wollen wir die obere Liste lediglich iterieren und jedes Element ausgeben, so können wir die Schleife
// auf diese Weise benutzen:
//
//               Liste
//               |
//          Zuweisungoperator (In dem Fall ein ntiver syntaktischer Operator, keine Methode der Objekte)
//          |    |
//   Iterator    |
//   |      |    |
for(element <- list) {
  println(element) // 2, 4, 5, 8, 9
}


// Die For comprehension kennt keine klassische Iteration über einen extra definierten Zähler. Lediglich foreach
// Ausdrücke sind möglich. Um dennoch eine 'Laufvariable' zu benutzen, existiert die Range Struktur, welche
// einen from-to - Ausdruck ermöglich. In dem Beispiel eine Range von 0 bis 10 in 2er Schritten.
// Neben Range gibt es noch eine Vielzahl weitere iterierbarer Strukturen.

for(element <- Range(0, 10, 2)) {
  println(element) // 0, 2, 4, 6, 8, 10
}


/** Filtering */
// Nun kommen wir zu einigen schönen Features, welche die for comprehension von normalen for Schleifen unterscheidet.

// Mit Filterausdrücken kann die Schleife aus einer iterierten Liste direkt Element aussortieren.
// Dafür schreibt man ein if, gefolgt einem boolschen Ausdruck direkt hinter den Iteratorausdruck.
// In diesem Beispiel iterieren wir wieder über die oben definierte Liste, filtern aber alle Elemente heraus,
// die nicht ganzzahlig durch 2 teilbar sind:
for(element <- list if element % 2 == 0) {
  println(element) // 2, 4, 8
}

// Filter können auch kombiniert werden. Hierfür steigen wir auf eine andere Schreibform um, wobei die einzelnen
// Ausdrücke einfach untereinander geschrieben werden. Man beachte hier die genutzten curly braces.
// In diesem Beispiel geben wir nur alle Elemente aus, die ganzzahlig durch 2 teilbar sind und größer als 2 sind:
for {
  element <- list
  if element % 2 == 0
  if element > 2
} {
  println(element) // 4, 8
}


/** Nested iterations */
// Nested Iterations sind eine elegante Möglichkeit über verschachtelte Sequencen zu iterieren oder Kreuzprodukte
// zu erzeugen und verschachtelte Schleifen wie in anderen Sprachen zu umgehen.

// Im folgenden Beispiel haben wir eine Liste von Namen, welche aus Vor- und Zunamen bestehen.
// Die Schleife iteriert nun über die Liste und der Iterator 'name' beinhaltet die vollständigen Namen.
// Darunter steht nun eine zweite Iterationsanweisung. Diese nimmt nun den 'name' Iterator, welcher ein String ist,
// und wendet darauf die 'split' Methode an. Diese Methode splittet den Namen und erzeugt ihrerseits eine Liste
// der einzelnen Wörter des Namens. Über diese neue Liste wird nun per Iterator 'word' wiederum iteriert.

// Schlussendlich geben wir den Iterator 'word' im Funktionsblock auf. Das Ergebnis ist
// "Max", "Mustermann", "Hans", "Dampf"
val names = List("Max Mustermann", "Hans Dampf")

for {
  name <- names             // Für jedes names erzeuge ein name
  word <- name.split(" ")   // Für jedes name.split(' ') erzeuge ein word
} {
  println(word) // "Max", "Mustermann", "Hans", "Dampf"
}


/** Nested iterations und Filter kombiniert. */
// Filteranweisungen und nested iterations lassen sich auch kombinieren.
// In diesem Beispiel wird wieder über die Liste mit den Namen iteriert. Nun filtern wir aber jeden Iterator
// zusätzlich. Schreiben wir alle Anweisungen unter einander, ergbist sich ein recht logischer Lesefluss von
// oben nach unten.
// Hier bleibt am Ende nur noch das Wort "Dampf" übrig.
for {
  name <- names
  if name.startsWith("H")   // Nur noch "Hans Dampf"
  word <- name.split(" ")
  if word.length > 4        // Nur noch "Dampf"
} {
  println(word) // "Dampf"
}


/** Yielding */
// Wie wir bereits beim If-Statement gesehen haben, sind sehr viele Ausdrücke in Scala Ausdrücke, die sich zu einem
// Endwert auflösen, oder grob gesagt, einen Rückgabewert besitzen. Auch eine for comprehension kann auf diese Weise
// benutzt werden, um Collections zu transformieren oder zu filtern.
// Dafür wird das Schlüsselwird 'yield' zwischen dem Iteratorblock und dem Anweisungsblock gesetzt. Dadurch werden
// die, vom Anweisungsblock, zurück gegebenen Werte (Immer der letzte Ausruck!), wieder in eine Collection gepackt
// in die sich die gesamte Schleife am Ende auflöst.

// Hier wird wieder über names iteriert und die einzelnen Wörter aus den Namen erzeugt. Im Anweisungsblock geben
// geben wir nun aber nicht das Wert aus stdout aus, sondern ermitteln die Wortlänge. Da dies der letzte Ausdruck
// vom Anweisungsblock ist, werden die einzelnen Wortlängen durch yield wieder in eine Collection verpackt und der
// Endwert dem Value 'wordLength' zugewiesen. 'wordLength' ist nun eine List[Int] mit der Länge eines jeden Worts.
val wordLength: List[Int] = for {
    name <- names
    word <- name.split(" ")
  } yield {
    word.length
  }
