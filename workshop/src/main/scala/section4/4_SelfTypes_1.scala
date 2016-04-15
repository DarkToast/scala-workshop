package section4

package object SelfTypes1 {

  case class Passenger(hasTicket: Boolean)

  /**
    * We define a train class hierachy. We have abstract trains and
    * concrete capitalism trains and socialism trains:
    */
  abstract class Train(var passengers: List[Passenger])

  class CapitalismTrain(passengers: List[Passenger]) extends Train(passengers)

  class SocialismTrain(passengers: List[Passenger]) extends Train(passengers)

  /**
    * We also define a trainguard, which will control if the passengers have tickets.
    * But in a socialism train there should be free rides for the masses!!! So
    * the conductor should only be allowed to control in a capitalism train.
    *
    * This self type defines, that a conductor instance always must be or must mixed in in a CapitalismTrain
    */
  trait TrainGuard {
    this: CapitalismTrain =>
    // A conductor can only control a capitalismTrain
    def findFareDodgers: Int = passengers.count(p => !p.hasTicket)
  }

  object SelfType extends App {
    val passengers = List(Passenger(true), Passenger(false), Passenger(true), Passenger(false))

    val capitalismTrain = new CapitalismTrain(passengers) with TrainGuard
    println(capitalismTrain.findFareDodgers)

    /*
     * WILL NOT COMPILE
    val socialismTrain = new SocialismTrain(passengers) with TrainGuard
    println(socialismTrain.findFareDodgers)
    */
  }

}
