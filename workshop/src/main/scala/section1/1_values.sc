/************************
 * OBJECTS und REFERENCES
 ************************/
// Zunächst einmal sind alle Werte in Scala Objekte. Auch Integers, Booleans etc.
// welche in anderen Sprachen lediglich als Primitive definiert sind.
2.toString
true.hashCode()

// Standardtypen:
val int: Int = 900000000
val long: Long = 9000000000000000000l
val double: Double = 2.2
val float: Float = 3.4F
val bool: Boolean = true
val char: Char = 'c'
val byte: Byte = 0xf
val string: String = "Hallo Welt"


// Da alle Wete auch Objekte sind, kennt Scala nur sehr wenig native Operatoren.
// Stattdessen sind 'normale' Additionsoperatoren wie '+' bei Integers lediglich
// Methoden eben dieser Klasse.
2.+(2)


// Da die obere Form doch etwas hakelig ist, erlaubt Scala Punkte und Klammern bei Aufrufen weg zu lassen.
// Der Compiler schafft es in den meisten Fällen die Reihenfolge der Argumente aufzulösen, wodurch dann auch wieder
// eine lesbare Form für die Addition entsteht.
2 + 2
2 equals 5


// Wenn Objekte verglichen werden, so bietet '==' einen Wertevergleich an und
// die Methode 'eq' einen Referenzvergleich. Kommt man von Java, so kennt man diese Operatoren
// genau entgegengesetzt und hatte sicher schon seine liebe Mühe mit den == Vergleichen von Objekten.
var l = List(1, 2, 3)
var ll = List(1, 2, 3)

l == ll   // Macht einen Wertevergleich (hier true)
l eq ll   // Macht einen Referenzvergleich (hier false)


/*****************
 * VALs und VARs
 *****************/
// Eine benannte Variable lässt sich mit 'var', dem Namen der Variable plus dem Typ
// nach einem Doppelpunkt definieren. Eine Wertinitialisierung ist immer erforderlich.
// Variablen sind, wie der Name bereits sagt, veränderbar (mutable) und können über die Zeit andere Werte annehmen.
var y: String = "This is a var"
y = "Another value"


// Im Gegensatz zu Variablen, definieren values eine immutable reference, äquivalent zu final
// Variablen in Java. Best practise ist hier: So viel vals wie möglich, so viel vars wie nötig.
// Vor allem wenn man im nebenläufigen Umfeld unterwegs ist, was bei Scala schnell passieren kann.
val x: String = "hello world"


// Wie wir bereits weiter oben gesehen haben, erlaubt uns der Scala Compiler manche Ausdrücke
// zu verkürzen. Bei vals und vars können wir den Typ weg lassen. Trotzdem haben die Bezeichner
// einen Typ. Der Compiler ermittelt den Typ implizit über den Initialisierungswert.
val xx = "hello other world"

// xx = 12 <-- Das geht nicht! xx ist vom Typ String.