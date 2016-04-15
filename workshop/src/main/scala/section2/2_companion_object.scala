package section2

/********************
 * COMPANION OBJECTS
 *******************/

// Möchte man nun ein statisches Element für alle Instanzen einer Klasse einführen,
// so bietet Scala die Möglichkeit eines companion objects.
// Hat ein object denselben Namen wie eine class, so hängen diese beiden Elemente
// logisch zusammen. So kann eine Instanz einer class auf die private member eines objects
// zugreifen und umgekehrt.

/**
  * Eine normale Klasse, mit zwei privaten Methoden.
  *
  * @param colour
  * @param age
  */
class Car(var colour: String, age: Int) {
  /**
    * Diese Methode ist `private` und lässt dieses Auto
    * mit einem anderen Auto zusammenstoßen.
    *
    * @param other
    */
  private def crashWith(other: Car) {
    this.explodeAirbags()
    other.explodeAirbags()
  }

  private def explodeAirbags() = {
    println("PUFFF!")
  }
}

/**
  * Dies ist das Companion Object. Es hat den selben Namen wie die obere class.
  * Somit kann in Methode `startTraffic` auf die `crashWith` Methode der übergebenen
  * `Car` Instanzen zugreifen.
  */
object Car {
  def startTraffic(cars: Car*) = {
    if(cars.size >= 2) {
      cars(0).crashWith(cars(1))
    }
  }

  /**
    * Ein weiterer Einsatzzweck der Companion objects sind Factorymethoden.
    * Scala bietet hierfür die spezielle Methode `apply` an. Dies erlaubt
    * eine Kurzschreibweise beim Aufrufen, wie wir weiter unten sehen.
    *
    * @param color
    * @param age
    * @return
    */
  def apply(color: String, age: Int): Car = {
    new Car(color, age)
  }
}


/**
  * Für einen Test erzeugen wir uns kurzerhand eine `main` Methode in einem statischen `Main` Object:
  */
object Main {
  def main(args: Array[String]) = {
    val firstCar = Car("blue", 3)   // Hier wird eigentlich Car.apply("blue", 3) aufgerufen.
    val secondCar = Car("red", 5)

    Car.startTraffic(firstCar, secondCar)
  }
}




