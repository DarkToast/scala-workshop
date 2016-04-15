/****************
 * Option
 ****************
  * is a container model for parameters and return values
 * which can be obsolete or nullable.
 *
 * It is designed to give developers the hint, that a method
 * can return an empty value.
 */

// Option ist ein Containerformat nach dem Monaden-Pattern, um optionale
// Werte zu repräsentieren. Dadurch sollen vor allem NullpointerExceptions
// verhindert, oder zumindest minimiert werden.
// Ein Option ist dabei nur ein abstrakter Oberwert und hat zwei
// abgeleitete Werte: Ein `None` object und eine `Some`
// Klasse.
/*
 *       Option[T]
 *          / \
 *           |
 *     +-----+--------+
 *     |              |
 *   None        Some[T](x: T)
 */
// So können wir einen leeren optionalen String definieren.
val o: Option[String] = None

// Ein `Some` wiederum hat tatsächlich einen Wert, den man mit der
// `Some` factory Methode erzeugen kann.
val oo: Option[String] = Some("hello World")
// Alle Options haben die selbe API:
o.isDefined           // true, wenn es ein `Some(value)` ist oder `false`, wenn es ein None ist.
o.getOrElse("foo")    // Wenn `o`ein None ist, return "foo"

// Auch haben Options die Scala Collection-API.
// So kann ein Option z.B. wie eine Liste behandelt werden.
// Mit `exists` und `contains` können wir bestimmen ob das Option einen
// bestimmten Wert enthält.
val exist: Boolean = oo.exists(value => value == "hello World")
val con: Boolean = oo.contains("hello World")
// Ebenfalls mit `map` und `flatMap` lässt sich ein Option[A] in ein Option[B] verwandelt.
// Wie hier ein Option[String] in ein Option[List[String]]
oo.map(value => value.split(' ').toList)


// Da auch Option die Collection-API implementiert,
// kann es sogar in einer for-comprehension benutzt werden.
// Die folgende for-comprehension extrahiert die Werte aus den beiden Options,
// konkateniert beide Werte im `yield` zusammen und gibt den
// Wert als zusammengesetztes Option zurück. Wäre auch nur ein Option
// ein `None`, so wäre auch der gesamte Ausdruck `None`.

val opt_hello = Some("Hello")
val opt_world = None// Some("World")

val value: Option[String] = for {
  hello <- opt_hello
  world <- opt_world
} yield {
  hello + " " + world
}


// `value` lässt sich jetzt zum Beispiel mit `foreach` ausgeben.
// Das sieht vielleicht was seltsam aus, da wir nur einen oder keinen
// Wert haben, aber so ist nunmal die Collection-API
value.foreach(println(_))

// Alternativ gibt es `getOrElse`, was entweder den Wert des Options, oder
// den angegebenen Defaultparameter zurück gibt:
println(value.getOrElse("nothing"))




