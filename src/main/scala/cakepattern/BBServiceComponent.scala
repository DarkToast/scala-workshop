package cakepattern

trait BBService {
  def callService(y: String)
}

trait BBServiceComponent {
  val bbService: BBService

  class BBServiceDefault extends BBService {
    def callService(y: String): Unit = println(s"bbService says: '$y'")
  }
}
