/************************************
 * FUNCTIONS - first class citizens
 ***********************************/

// Funktionen verhalten sich in ihrer Grundform genauso wie Methoden. Sie haben Eingabeparameter und einen
// Rückgabewert.
// Zusätzlich sind Funktionen aber auch so genannte first class citizens. Also Objekte, die genauso
// wie ein String, ein Array oder ein komplexes Objekt referenziert und z.B. als Parameter übergeben
// werden können.


// Grundsätzlicher Aufbau einer function:
  (x: Int, y: Int) => { x+y }
// |       |         |       |
// |       |         +-------+
// |       |             |
// |       |         function body
// |       |
// |       second parameter
// |
// first parameter


// Wollen wir nun eine Funktion referenzieren, so können wir dies wie jede andere Variablendefinition machen.
// Der Typ einer Funktion sieht dabei so ähnlich aus, wie ihre Definition und mag am Anfang ein wenig verwirren.

val g: (Int, Int) => Int   =   (x: Int, y: Int) => { x+y }
//     |                |      |                         |
//     +----------------+      +-------------------------+
//       |                       |
//       The function type       |
//                               |
//                               function definition

// In diesem Beispiel ist der Name des Value 'g'.
// Der Typ des Value ist '(Int, Int) => Int' - Sprich: Int und Int auf Int.
// Dieser Typ beschreibt alle Funktionen, welche zwei Int Parameter und als Rückgabetyp wiederum ein Int besitzen.
// Der Wert ist nun die eigentliche Funktion mit zwei benannten Parametern (x und y) und einem Funktionskörper,
// der diese beiden Parameter addiert. Möglich ist hier auch eine Multiplikation oder jede andere denkbare
// Transformation, die wiederum ein Int zurück gibt.

// Die Funktion ist nun über den Namen 'g' referenzier- und dadurch auch direkt aufrufbar:
g(1, 2)


// Auch bei Funktionen müssen wir nicht jedesmal den Typ angeben oder Klammern setzen.
// Gerade bei Funktionen macht die explizite Angabe des Typs den Code mitunter unübersichtlicher.
// Allerdings können wir nicht alle Typdefinitionen weg lassen. Der Compile muss immer den Typ
// der Funktion ermitteln können.

val h = (x: Int, y: Int) => x+y   // Durch die Parameter und die Operation sind alle Typen bekannt

val i: (Int, Int) => Int  =  (x, y) => x+y  // Durch den expliziten Typ des Values, brauchen wir die Typen der Parameter
                                            // nicht mehr mit angeben. Diese sind implizit bekannt.




/**********************************
 * HIGHER ORDER FUNCTIONS/METHODS
 **********************************/
// Eine Higher order function ist eine Funktion, die wiederum eine Funktion als Parameter übergeben bekommt und/oder
// eine Funktion zurück gibt. Nicht nur allein Funktionen können so benutzt werden, sondern auch Methoden. Hier sind
// beide wieder äquivalent.

// Diese Methode hat zwei Parameter. Einmal x vom Typ 'Int' und f vom Typ 'Int => Int'.
// Zusätzlich definieren wir eine Funktion, die unserem Parametertyp von f entspricht und benutzen sie
// als zweiten Parameter für 'makeSomethingWithInt'

def makeSomethingWithInt(x: Int, f: (Int) => Int): Int = f(x)

val twoTimes = (value: Int) => value * 2
makeSomethingWithInt(42, twoTimes)


// Genauso können wir auch eine Methode dazu benutzen uns eine neue Funktion zu erzeugen.
// 'createNTimes' bekommt als Parameter ein Int 'times' und erzeugt daraus eine Funktion, deren
// erster Parameter 'x' mit 'times' multipliziert wird. Diese Funktion wird dann zurück gegeben.
def createNTimes(times: Int): (Int) => Int = {
  (x: Int) => times * x
}

val threeTimes: (Int) => Int = createNTimes(3)
makeSomethingWithInt(42, threeTimes)


/***********
 * LAMBDAS
 ***********/
// In vielen Fällen brauchen wir eine Funktion nur genau einmal. Uns reicht also aus, wenn eine Funktionsdefinition
// anonym direkt als Parameter übergeben wird. Dies ist auch als anonymous function oder lambda expression bekannt.
// In den folgenden Beispielen wird immer wieder dieselbe anonyme Funktion der Methode 'makeSomethingWithInt'
// übergeben. Dabei lassen wir aber nach und nach nicht notwendigen Klammern oder Typen weg. Dadurch lassen sich
// Lambdas sehr effizient schreiben.

// Vollständiger Lambdaausdruck
makeSomethingWithInt(8, (x: Int) => {
  x * 2
})

// Der Codeblock ist ein Einzeiler, daher können wir die {}-Klammern weg lassen.
makeSomethingWithInt(8, (x: Int) => x * 2)

// Der Compiler weiß anhand des Parametertyps von 'makeSomethingWithInt' bereits das x ein Int sein muss.
// Daher können wir auch den Typ im Lambda weglassen.
makeSomethingWithInt(8, (x) => x * 2)

// Die () - Klammern brauchen wir nun auch nicht mehr.
makeSomethingWithInt(8, x => x * 2) // shorter

// Die kürzeste Form eines Lambdas besteht nur noch aus Funktionsblock und den Wildcardzeichen _
// Jedes _ steht dabei für einen Parameter aus der Parameterliste, wobei jedes Wildcard nur genau einmal
// benutzt werden kann. Diese Syntax sollte explizit sehr kurzen Funktionen vorbehalten bleiben. Ansonsten droht
// ein nicht zu vernachlässigender Übersichtsverlust.
makeSomethingWithInt(8, _ * 2)

// Hätten wir eine Funktion mit (Int, Int) => Int sähe ein Codeblock mit Wildcards z.B so aus:
// _ + _ + 2, wobei das erste _ für das erste Int und das zweite _ für das zweite Int steht.


// Ein Einsatzgebiet dieser sehr kurzen Notation ist z.B. die map Methode einer Scala Liste.
// In unserem Beispiel haben wir eine Liste von Ints. Die map Methode verlangt nun als Parameter
// eine Funktion vom Typ '(Int) => Int'. Diese Funktion wird daraufhin auf jedes Element der Liste
// angewandt und die Ergebnisse werden in eine neue Liste verpackt. Als Ergebnis erhalten wir
// eine neue List(2, 4, 6)
val list = List(1, 2, 3)
list.map(_ * 2)
