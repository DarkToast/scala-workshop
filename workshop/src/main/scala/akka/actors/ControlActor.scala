package akka.actors

import java.io.IOException
import java.nio.file.{Files, Paths}

import akka.SchedulerConfig
import akka.actor.{Actor, ActorRef, Cancellable}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ControlActor(databaseActorRef: ActorRef) extends Actor {

  var jobScheduler: Option[Cancellable] = None

  override def receive: Receive = {
    case "Update" => {
      val configState: Boolean = getConfigState
      val schedulerState: Boolean = jobScheduler.isDefined && !jobScheduler.get.isCancelled

      val id: Long = Thread.currentThread.getId
      println(s"[ControlActor, $id] - Reading state. State is $configState.")

      if (configState && !schedulerState) {
        println(s"[ControlActor, $id] - Starting the job scheduler.")
        jobScheduler = Some(context.system.scheduler.schedule(50.milliseconds, SchedulerConfig.databaseInterval.seconds, databaseActorRef, "Tick"))
      }
      else if (!configState && schedulerState) {
        println(s"[ControlActor, $id] Stopping the job scheduler.")
        jobScheduler.get.cancel
      }

    }
  }

  private def getConfigState: Boolean = {
    try {
      new String(Files.readAllBytes(Paths.get("./trigger.txt"))) == "true"
    } catch {
      case e: IOException => false
    }
  }
}
