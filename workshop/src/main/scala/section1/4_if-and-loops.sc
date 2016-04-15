/**************
 * If
 **************/

// Eine If Verzweigung funktioniert in Scala ein bisschen anders, als in
// klassischen imperativen Sprachen. Während ein If z.B. in Java lediglich
// die aktiven Verzweigungsblöcke ausführt, ist ein Scala-If ein vollständiger
// Ausdruck, der, wie fast alle Ausdrücke in Scala, den Rückgabewert seiner aktiven Blöcke hat.

val x = 12
val value = if (x == 42) {
    "Die Antwort"
  } else {
    "Irgendwas anderes"
  }

// In diesem Beispiel wird den Wert x mit 42 verglichen. Je nachdem was gilt, wird der entsprechende
// Block ausgeführt und zum darin enthaltenen String ausgewertet. Das If wiederum wertet sich daraufhin
// zu genau diesem Wert aus, woraufhin dieser dem Wert 'value' zugewiesen wird.



/**************
 * WHILE LOOP
 **************/
// Die While Schleife ist einer der wenigen Ausdrücke die immer Unit zurück geben. Also im Kern
// seiteneffektbehaftet ist. Ansonsten funktioniert sie wie in Java.
var y = 1
while(y != 4) {
  println(y)
  y += 1
}

// Äquivalent zur while Schleife, existiert auch die do while:
var z = 1
do {
  println(z)
  z += 1
} while(z != 4)