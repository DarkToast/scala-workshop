package cakepattern

// A trait, which describes the service AA.
trait AAService {
  def callService(x: String)
}

// A component trait, which defines an instance of the service AA
trait AAComponent {
  val aaService: AAService

  class AAServiceDefault extends AAService {
    def callService(x: String): Unit = println(s"AAService says: '$x'")
  }
}
