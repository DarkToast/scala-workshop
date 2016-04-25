package akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool

object Introduction {
  def main(args: Array[String]) {
    val system: ActorSystem = ActorSystem.create("Example")

    val consumer: ActorRef = system.actorOf(Props[Consumer])

    val producerRouter: ActorRef = system.actorOf(RoundRobinPool(3).props(Props(new Producer(consumer))), "router")
    val producer: ActorRef = system.actorOf(Props(new Producer(consumer)))

    for(i <- 0 to 20) {
      producer ! "Start"
    }
  }
}

class Producer(val consumer: ActorRef) extends Actor {

  override def receive: Receive = {
    case "Start" => {
      val threadId = Thread.currentThread().getId
      println(s"Got a Start! Will ping.- $threadId")
      Thread.sleep(500)
      consumer ! "Ping"
    }

    case "Pong" => {
      val threadId = Thread.currentThread().getId
      println(s"Got a pong! - $threadId")
    }
  }

}

class Consumer extends Actor {

  override def receive: Receive = {
    case "Ping" => {
      val threadId = Thread.currentThread().getId
      println(s"Got a ping! - $threadId")
      sender ! "Pong"
    }
  }

}
