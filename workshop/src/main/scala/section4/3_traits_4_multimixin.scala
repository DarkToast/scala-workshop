package section4

/**
  * This example shows how we can multi mixin traits in classes
  * and stumble upon a problem called the "diamond of death"
  * `https://en.wikipedia.org/wiki/Multiple_inheritance#The_diamond_problem`
  */

  /**
   * Let us define an abstract type `Engine` with two implementations
   */
  trait Engine {
    def run()
  }

  trait BigEngine extends Engine {
    override def run() = println("RRÖÖÖÖÖÖÖÖÖÖAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRR")
  }

  trait LittleEngine extends Engine {
    override def run() = println("töf töf töf")
  }

  /** Let us define a super special hybrid car with the two engines: **/
  class Car extends LittleEngine with BigEngine {
    // We can call the concrete super method by giving the type information to the `super` reference.
    def runLittle() = super[LittleEngine].run() // --> calls The LittleEngine.run
    def runBig() = super[BigEngine].run()       // --> calls the BigEngine.run
  }

  /**
   * We instantiate a car. But the question is, which engine will start, if we call the `run` method?
   */
  object MultiMixin extends App {
    // This ist our special car with the two engines:
    val car = new Car

    // But which engine will start by just calling `run` ?
    car.run()
    car.runLittle()
    car.runBig()


    // The answer is BigEngine.run. Because scala goes an easy way to solve the diamond problem:
    // It just uses the traits order form right to left and therefore: First come first serve.
    // So because we mixin the traits with `extends LittleEngine with BigEngine`, BigEngine wins.
    // If we switch the statement to `extends BigEngine with LittleEngine` the run method would call
    // the super method of LittleEngine.
  }
