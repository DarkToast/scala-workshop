package section1

// `HelloWorld` ist ein `object`. Ein statisches Singleton-Element in Scala.
object HelloWorld {

  // Da wir auf der JVM sind, ist unser zentraler Einstiegspunkt die `main`-Methode.
  def main (args: Array[String]) {

    // Args ist ein Array von Strings.
    // Das IF schaut, ob args länger als 1 ist. Wenn, dann greifen wir auf das 1ste Element zu. Man beachte
    // die runden Klammern.
    // Das IF gibt direkt den String aus seinen Entscheidungsblöcken zurück und speichert es in der Variable
    // `message`. Ebenfalls vom Typ `String`.
    val message: String = if(args.length >= 1) {
      "Hello World. My first arg is: " + args(1)
    } else {
      "Hello World. No args given"
    }

    // Schlussendlich geben wir `message` aus.
    println(message)
  }

}
