package cakepattern

import scala.Predef._

trait RuntimeEnvironment
  extends AAComponent
  with BBServiceComponent {

  val aaService: AAService = new AAServiceDefault
  val bbService: BBService = new BBServiceDefault
}

trait MockedTestEnvironment
  extends AAComponent
  with BBServiceComponent {

  val aaService: AAService = new AAService {
    def callService(x: String): Unit = println("mocked AAServive")
  }

  val bbService: BBService = new BBService {
    def callService(y: String): Unit = println("mocked BBService")
  }
}
