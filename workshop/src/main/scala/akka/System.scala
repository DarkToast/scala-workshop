package akka

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.actors.{ControlActor, DatabaseActor, WorkerActor}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object System {
  def main(args: Array[String]) {
    val system: ActorSystem = ActorSystem.create("Scheduler")

    val worker: ActorRef = system.actorOf(Props[WorkerActor])
    val database: ActorRef = system.actorOf(Props(new DatabaseActor(worker)))
    val controller: ActorRef = system.actorOf(Props(new ControlActor(database)))

    system.scheduler.schedule(0.seconds, SchedulerConfig.controllerInterval.seconds, controller, "Update")
  }

}
