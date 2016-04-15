/**************************
 * CASE CLASSES
 **************************/

// Scala bietet für Valueobjekte eine spezielle Art der Klasse, genannt `case class`
// Definiert wird sie wie jede andere Klasse, aber mit einem `case` Schlüsselwort.

// Case classes bieten ein paar vordefinierte Methoden, die viel Boilerplatecode einsparen.
// So sind in diesem Beispiel die Konstruktorargumente automatisch public und immutable.
// Die Klasse bietet direkt eine `equals` und `hashCode` Methode, die alle Werte der Klasse
// mit einbeziehen. Auch existiert für das value object pattern eine `copy` Methode,
// welche über named parameters nur spezielle Werte in der Kopie ändern lassen.
case class User(name: String, surname: String, age: Int) {
  // Ein Beispiel für die `copy` Methode. Wir erzeugen eine Kopie der aktuellen Instanz
  // aber mit einem neuen Alter.
  def birthday = this.copy(age = age + 1)
}

// Case classes besitzen auch direkt ein companion object mit einer apply Methode:
val user_one = User("Max", "Mustermann", 23)
val user_two = User("Max", "Mustermann", 23)

// Beispiel für `equals` und `hashCode`.
user_one == user_two
user_one.hashCode

// Auch haben case classes eine automatische toString Implementierung, die alle
// Werte ausdruckt.
// 'User(Max,Mustermann,23)'
println(user_one)